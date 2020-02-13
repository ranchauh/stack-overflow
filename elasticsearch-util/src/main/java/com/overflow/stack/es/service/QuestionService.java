package com.overflow.stack.es.service;

import com.overflow.stack.es.model.Question;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface QuestionService {

    Question save(Question question);

    void deleteById(String questionId);

    String update(Question question) throws IOException;

    Page<Question> findByTag(String tag, Pageable pageable);

    Page<Question> findByText(String searchText, Pageable pageable);

    Page<Question> findLatest(Pageable pageable);

    Optional<Question> findById(String questionId);

    List<? extends Terms.Bucket> findAllTags();

}
