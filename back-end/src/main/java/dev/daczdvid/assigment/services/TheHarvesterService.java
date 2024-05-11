package dev.daczdvid.assigment.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Frame;
import dev.daczdvid.assigment.repository.FileRepository;
import dev.daczdvid.assigment.dockerClient.MyDockerClient;
import dev.daczdvid.assigment.model.Scan;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
public class TheHarvesterService {

    @Autowired
    FileRepository fileRepository;

    public Scan scanWithTheHarvester(String domain, String datasource) {
        try{
            Long id = (long) fileRepository.numberOfScans();
            String begin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
            fileRepository.writeScanResultToJson(new Scan(
                    id,
                    domain,
                    true,
                    "theharvester",
                    new ArrayList<>(),
                    new ArrayList<>(),
                    begin,
                    null
            ));

            System.out.println("Initiating docker client");
            DockerClient dockerClient = new MyDockerClient().getInstance();
            System.out.println("Pulling from docker client");
            dockerClient.pullImageCmd("simonthomas/theharvester")
                    .exec(new PullImageResultCallback())
                    .awaitCompletion(120, TimeUnit.SECONDS);
            System.out.println("Pulled complete");
            CreateContainerResponse container = dockerClient.createContainerCmd("simonthomas/theharvester")
                    .withCmd("theharvester", "-d", domain, "-l", "100", "-b", datasource)
                    .exec();

            System.out.println("Running commadn");

            String containerId = container.getId();
            dockerClient.startContainerCmd(containerId).exec();
            StringBuilder outputBuffer = new StringBuilder();

            dockerClient.attachContainerCmd(containerId)
                        .withStdOut(true)
                        .withStdErr(true)
                        .withFollowStream(true)
                        .exec(new ResultCallback.Adapter<>() {
                            @Override
                            public void onNext(Frame frame) {
                                outputBuffer.append(new String(frame.getPayload()));
                            }
                        })
                        .awaitCompletion();

            String output = outputBuffer.toString();
            List<String> domainsAndIps = extractDomainsAndIPs(output);
            List<String> emails = extractEmails(output);
            List<String> users = extractUsers(output);
            emails.addAll(users);
            String end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();

            Scan result = new Scan(
                    id,
                    domain,
                    false,
                    "theharvester",
                    emails,
                    domainsAndIps,
                    begin,
                    end
            );

            fileRepository.updateScan(result);
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> extractDomainsAndIPs(String output) {
        List<String> domainsAndIPs = new ArrayList<>();

        String patternString = "([a-zA-Z0-9.-]+) : ([a-zA-Z0-9.]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(output);

        while (matcher.find()) {
            String domain = matcher.group(1).trim();
            String ip = matcher.group(2).trim();
            domainsAndIPs.add(domain + " : " + ip);
        }

        return domainsAndIPs;
    }
    public static List<String> extractEmails(String output) {
        List<String> emails = new ArrayList<>();

        String patternString = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(output);

        while (matcher.find()) {
            String email = matcher.group().trim();
            emails.add(email);
        }
        emails.remove("cmartorella@edge-security.com");
        return emails;
    }

    public static List<String> extractUsers(String output) {
        List<String> users = new ArrayList<>();

        // Pattern to match users and emails
        String patternString = "(Users from Linkedin:.*?\\n)(.*?)(?=\\n\\n|$)";
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(output);

        while (matcher.find()) {
            String usersBlock = matcher.group(2).trim();
            String[] tmpusers = usersBlock.split("\\n");

            for (String user : tmpusers) {
                if(user.contains(" - ")) {
                    users.add(user);
                }
            }
        }
        return users;
    }
}
