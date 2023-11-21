package com.developez;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog App REST APIs",
                description = "Spring Boot Blog App REST APIs Documentation",
                version = "v1.0.0",
                contact = @Contact(
                        name = "Developez.com",
                        email = "adienerlopez@gmail.com",
                        url = "https://www.linkedin.com/in/adiener-lopez-934089236/"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog App Source Code",
                url = "https://github.com/Pochyxi/JAVA_SPRING_BOOT_blog-API_SpringDoc.git"
        )
)
@SpringBootApplication
@PropertySource("classpath:application-secret.properties")
@EntityScan(basePackages = {"com.developez.*"})
@EnableJpaRepositories(basePackages = {"com.developez.*"})
public class SpringBootBlogApiApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main( String[] args ) {

        SpringApplication.run( SpringBootBlogApiApplication.class, args );
    }
}
