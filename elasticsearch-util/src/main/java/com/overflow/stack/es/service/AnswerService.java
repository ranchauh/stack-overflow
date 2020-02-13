package com.overflow.stack.es.service;

import com.overflow.stack.es.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;


public interface AnswerService {

    Answer save(Answer answer);

    void deleteById(String answerId);

    String update(Answer answer) throws IOException;

    Page<Answer> findByText(String searchText, Pageable pageable);

    Page<Answer> findLatest(Pageable pageable);

    Page<Answer> findByParentAnswerId(String parentAnswerId, Pageable pageable);

    Page<Answer> findByQuestionId(String questionId, Pageable pageable);

    Optional<Answer> findById(String answerId);
}
