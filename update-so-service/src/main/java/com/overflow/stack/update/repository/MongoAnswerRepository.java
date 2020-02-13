package com.overflow.stack.update.repository;

import com.overflow.stack.es.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAnswerRepository extends MongoRepository<Answer,String> {
}
