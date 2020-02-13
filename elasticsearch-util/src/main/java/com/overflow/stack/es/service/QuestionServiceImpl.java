package com.overflow.stack.es.service;

import com.overflow.stack.es.model.Question;
import com.overflow.stack.es.repository.QuestionRepository;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteById(String questionId){
        questionRepository.deleteById(questionId);
    }


    @Override
    public Page<Question> findByTag(String tag, Pageable pageable) {
        return questionRepository.findByTag(tag,pageable);
    }

    @Override
    public Page<Question> findByText(String searchText, Pageable pageable) {
        return questionRepository.findByText(searchText,searchText,pageable);
    }

    @Override
    public Page<Question> findLatest(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public Optional<Question> findById(String questionId) {
        return questionRepository.findById(questionId);
    }

    @Override
    public List<? extends Terms.Bucket> findAllTags(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withSearchType(SearchType.DEFAULT)
                .withIndices("questions").withTypes("question")
                .addAggregation(AggregationBuilders.terms("tags").field("tags"))
                .build();
        Aggregations aggregations = elasticsearchOperations.query(searchQuery, response -> response.getAggregations());
        return ((ParsedStringTerms)aggregations.asMap().get("tags")).getBuckets();
    }

    @Override
    public String update(Question question) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("questions")
                .type("question")
                .id(question.getQuestionId())
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("questionTitle",question.getQuestionTitle())
                        .field("questionDescription",question.getQuestionDescription())
                        .field("tags",question.getTags())
                        .field("voteCount",question.getVoteCount())
                        .field("updateTimestamp",question.getUpdateTimestamp())
                        .endObject());
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withIndexName("questions")
                .withId(question.getQuestionId())
                .withUpdateRequest(updateRequest)
                .withClass(Question.class)
                .build();
        UpdateResponse response = elasticsearchOperations.update(updateQuery);
        return response.getResult().getLowercase();
    }

}
