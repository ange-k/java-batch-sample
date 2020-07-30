package chalkboard.me.recovery_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecoveryBatchApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(RecoveryBatchApplication.class);
		// disable web container
		application.setWebApplicationType(WebApplicationType.NONE);
		System.exit(SpringApplication.exit(application.run(args)));
	}

}
