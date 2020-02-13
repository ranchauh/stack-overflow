package com.overflow.stack.service.repository;

import com.overflow.stack.service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository  extends MongoRepository<User,String> {
}
