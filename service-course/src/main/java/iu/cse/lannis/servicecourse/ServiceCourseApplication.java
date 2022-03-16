package iu.cse.lannis.servicecourse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
//@OpenAPIDefinition(info =
//    @Info(title = "Course Service Application API", version = "1.0.0", description = "Documentation Course Service Application v1.0.0")
//)
public class ServiceCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCourseApplication.class, args);
    }

}
