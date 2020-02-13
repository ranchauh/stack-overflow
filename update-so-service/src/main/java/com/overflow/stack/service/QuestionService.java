package com.overflow.stack.service;

import com.overflow.stack.es.model.Question;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface QuestionService {

    Optional<Question> find(String questionId);

    Question save(Question question);

    Question update(Question question);

    void delete(String questionId);

}
