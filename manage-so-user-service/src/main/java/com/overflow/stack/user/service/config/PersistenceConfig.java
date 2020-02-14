package com.overflow.stack.user.service.config;

import com.overflow.stack.user.service.repository.UserRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class PersistenceConfig {
}