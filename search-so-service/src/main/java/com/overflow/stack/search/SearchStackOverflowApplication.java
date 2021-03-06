package com.overflow.stack.search;

import com.overflow.stack.commons.SoCommonsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {SoCommonsConfiguration.class,SearchStackOverflowApplication.class})
public class SearchStackOverflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchStackOverflowApplication.class, args);
    }
}
