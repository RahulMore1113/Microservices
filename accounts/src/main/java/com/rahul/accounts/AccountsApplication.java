package com.rahul.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts Microservice REST API Documentation",
                description = "EazyBank Accounts Microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Rahul More",
                        email = "rahul@gmail.com",
                        url = "https://flowcv.me/rahul-more"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://flowcv.me/rahul-more"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "EazyBank Accounts Microservice REST API Documentation",
                url = "https://flowcv.me/rahul-more"
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
