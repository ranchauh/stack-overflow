package com.overflow.stack.update;

import com.overflow.stack.commons.SoCommonsConfiguration;
import com.overflow.stack.user.service.ManageSOUserApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SoCommonsConfiguration.class, ManageSOUserApplication.class})
public class UpdateStackOverflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpdateStackOverflowApplication.class, args);
    }
}
