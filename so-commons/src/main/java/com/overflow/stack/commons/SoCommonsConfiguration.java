package com.overflow.stack.commons;

import com.overflow.stack.commons.config.MediaFileStorageConfig;
import com.overflow.stack.commons.config.SoEsConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SoEsConfig.class, MediaFileStorageConfig.class})
@ComponentScan(basePackageClasses = SoCommonsConfiguration.class)
public class SoCommonsConfiguration {
}