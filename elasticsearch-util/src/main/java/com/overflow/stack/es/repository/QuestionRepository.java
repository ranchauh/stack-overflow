package com.overflow.stack.es.repository;

import com.overflow.stack.es.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ElasticsearchRepository<Question,String> {

    @Query("{\"bool\": {\"must\": {\"match_all\": {}}, \"filter\": {\"term\": {\"tags\": \"?0\" }}}}")
    Page<Question> findByTag(String tag, Pageable pageable);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"questionTitle\": \"?0\"}},{\"match\": {\"questionDescription\": \"?1\"}}]}}")
    Page<Question> findByText(String titleSearchText, String descSearchText, Pageable pageable);

}

