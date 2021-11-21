package iu.cse.lannis.serviceretention;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceRetentionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRetentionApplication.class, args);
    }

}
