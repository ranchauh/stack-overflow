package com.overflow.stack.update.es;

import com.overflow.stack.commons.SoCommonsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {SoCommonsConfiguration.class,UpdateElasticsearchApplication.class})
public class UpdateElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpdateElasticsearchApplication.class, args);
    }
}
