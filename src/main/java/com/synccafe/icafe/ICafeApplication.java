package com.synccafe.icafe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
   servers = {
     @Server(url = "http://localhost:8383", description = "LOCAL"),
     @Server(url = "http://upc-icafebackend-3sger0-aa823d-31-97-13-234.traefik.me/", description = "PRODUCTION")
   }
)
@SpringBootApplication
@EnableJpaAuditing
public class ICafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ICafeApplication.class, args);
    }

}
