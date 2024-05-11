package dev.daczdvid.assigment.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
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
public class AmassService {

    @Autowired
    FileRepository fileRepository;

    public Scan scanWithAmass(String domain) {
        try{
            Long id = (long) fileRepository.numberOfScans();
            String begin =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
            fileRepository.writeScanResultToJson(new Scan(
                    id,
                    domain,
                    true,
                    "amass",
                    new ArrayList<>(),
                    new ArrayList<>(),
                    begin,
                    null
            ));
            DockerClient dockerClient = new MyDockerClient().getInstance();

            dockerClient.pullImageCmd("caffix/amass")
                    .exec(new PullImageResultCallback())
                    .awaitCompletion(120, TimeUnit.SECONDS);
            System.out.println("DOcker pulled");
            CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd("caffix/amass")
                    .withCmd("enum", "-d", domain);

            System.out.println("Docker created");

            String containerId = createContainerCmd.exec().getId();
            dockerClient.startContainerCmd(containerId).exec();

            StringBuilder outputBuffer = new StringBuilder();

            dockerClient.attachContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .exec(new ResultCallback.Adapter<>() {
                        @Override
                        public void onNext(Frame frame) {
                            System.out.println(frame.toString());
                            outputBuffer.append(new String(frame.getPayload()));
                        }
                    })
                    .awaitCompletion();

            String output = outputBuffer.toString();
            List<String> domainsAndIps = extractSubdomains(output);
            String end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
            Scan result = new Scan(
                    id,
                    domain,
                    false,
                    "amass",
                    new ArrayList<>(),
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

    public static List<String> extractSubdomains(String output) {
        List<String> subdomains = new ArrayList<>();

        String patternString = "(\\S+\\.\\S+) \\(FQDN\\)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(output);

        while (matcher.find()) {
            String subdomain = matcher.group(1);
            subdomains.add(subdomain);
        }

        return subdomains;
    }

}
