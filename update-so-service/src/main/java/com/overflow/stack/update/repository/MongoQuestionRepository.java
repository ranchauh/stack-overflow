package com.overflow.stack.update.repository;

import com.overflow.stack.es.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoQuestionRepository extends MongoRepository<Question,String> {
}
