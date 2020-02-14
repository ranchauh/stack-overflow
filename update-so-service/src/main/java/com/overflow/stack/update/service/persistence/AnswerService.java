package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.Answer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AnswerService {

    Optional<Answer> find(long answerId);

    Answer save(long questionId,Answer answer);

    Answer update(long answerId,Answer answer);

    void delete(long answerId);

    Answer voteAnswer(Long answerId);

    Answer saveChildAnswer(Long parentAnswerId, Answer answer);
}
