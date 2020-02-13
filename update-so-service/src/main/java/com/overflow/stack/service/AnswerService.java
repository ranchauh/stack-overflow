package com.overflow.stack.service;

import com.overflow.stack.es.model.Answer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AnswerService {

    Optional<Answer> find(String answerId);

    Answer save(Answer answer);

    Answer update(Answer answer);

    void delete(String answerId);

}
