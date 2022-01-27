package iu.cse.lannis.servicestudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class ServiceStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceStudentApplication.class, args);
	}
}
