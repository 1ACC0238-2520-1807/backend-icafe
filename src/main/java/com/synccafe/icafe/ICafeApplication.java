package com.synccafe.icafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ICafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ICafeApplication.class, args);
    }

}
