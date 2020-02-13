package com.overflow.stack.search;

import com.overflow.stack.es.SoElasticsearchConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {SoElasticsearchConfiguration.class,SearchStackOverflowApplication.class})
public class SearchStackOverflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchStackOverflowApplication.class, args);
    }
}
