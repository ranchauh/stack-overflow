package com.overflow.stack.search.service;

import com.overflow.stack.commons.model.Answer;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface SearchAnswerService {

    Optional<Answer> getAnswerById(String answerId);

    Page<Answer> getAnswersByParentId(String parentAnswerId,int page,int size);

    Page<Answer> getAnswersByText(String searchText, int page, int size);

    Page<Answer> getAnswerByQuestionId(String questionId, int page, int size);
}
