package dev.daczdvid.assigment.dockerClient;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.net.URI;

@Component
@NoArgsConstructor
public class MyDockerClient {

	private DockerClient dockerClient;

    public DockerClient getInstance() {
		if(this.dockerClient == null) {
			System.out.println("Creating docker client");
			DockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
					.dockerHost(URI.create("unix:///var/run/docker.sock"))
					.build();
			DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder();

			System.out.println("Docker client created");

			dockerClient = DockerClientBuilder.getInstance().withDockerHttpClient(dockerHttpClient).build();
		}
		System.out.println("Returning docker client");
		return dockerClient;
	}
}
