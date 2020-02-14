package com.overflow.stack.commons.repository.es;

import com.overflow.stack.commons.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsAnswerRepository extends ElasticsearchRepository<Answer,String> {
    @Query("{\"bool\": {\"should\": [{\"match\": {\"answerText\": \"?0\"}}]}}")
    Page<Answer> findByText(String searchText, Pageable pageable);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"parentAnswerId\": \"?0\"}}]}}")
    Page<Answer> findByParentAnswerId(String parentAnswerId, Pageable pageable);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"questionId\": \"?0\"}}]}}")
    Page<Answer> findByQuestionId(String questionId, Pageable pageable);
}
