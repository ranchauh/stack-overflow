package com.overflow.stack.search.service;

import com.overflow.stack.es.model.Question;
import com.overflow.stack.search.model.SearchTagResult;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface SearchQuestionService {

    Page<Question> getTopQuestions(int pageNumber, int size);

    Page<Question> searchQuestionsByText(String searchText, int pageNumber, int size);

    Page<Question> searchQuestionsByTag(String tag, int pageNumber, int size) ;

    Optional<Question> getQuestionById(String questionId);

    SearchTagResult searchTag();

    SearchTagResult searchTag(String query);

}
