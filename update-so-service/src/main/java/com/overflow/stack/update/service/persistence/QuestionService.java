package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.Question;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface QuestionService {

    Optional<Question> find(long questionId);

    Question save(Question question);

    Question update(long questionId,Question question);

    void delete(long questionId);

    Question voteQuestion(long questionId);

}
