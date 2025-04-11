package dgtic.core.M8P1;

import dgtic.core.M8P1.Util.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class M8P1Application {

	public static void main(String[] args) {
		SpringApplication.run(M8P1Application.class, args);
	}

}
