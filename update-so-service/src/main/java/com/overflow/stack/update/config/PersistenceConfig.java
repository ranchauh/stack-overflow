package com.overflow.stack.update.config;

import com.overflow.stack.update.repository.persistence.AnswerRepository;
import com.overflow.stack.user.service.repository.UserRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = {AnswerRepository.class, UserRepository.class})
public class PersistenceConfig {
}