package com.overflow.stack.commons.service;

import com.overflow.stack.commons.model.Answer;
import com.overflow.stack.commons.repository.es.EsAnswerRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
public class EsAnswerServiceImpl implements EsAnswerService {

    @Autowired
    private EsAnswerRepository esAnswerRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public Answer save(Answer answer) {
        return esAnswerRepository.save(answer);
    }

    @Override
    public void deleteById(String answerId){
        esAnswerRepository.deleteById(answerId);
    }

    @Override
    public Page<Answer> findByText(String searchText, Pageable pageable) {
        return esAnswerRepository.findByText(searchText,pageable);
    }

    @Override
    public Page<Answer> findLatest(Pageable pageable) {
        return esAnswerRepository.findAll(pageable);
    }

    @Override
    public Page<Answer> findByParentAnswerId(String parentAnswerId, Pageable pageable) {
        return esAnswerRepository.findByParentAnswerId(parentAnswerId,pageable);
    }

    @Override
    public Page<Answer> findByQuestionId(String questionId, Pageable pageable) {
        return esAnswerRepository.findByQuestionId(questionId,pageable);
    }

    @Override
    public Optional<Answer> findById(String answerId) {
        return esAnswerRepository.findById(answerId);
    }

    @Override
    public String update(Answer answer) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("answers")
                .type("answer")
                .id(answer.getAnswerId())
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("answerText",answer.getAnswerText())
                        .field("voteCount",answer.getVoteCount())
                        .field("updateTimestamp",answer.getUpdateTimestamp())
                        .field("imageIds",answer.getImageIds())
                        .field("videoIds",answer.getVideoIds())
                        .endObject());
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withIndexName("answers")
                .withId(answer.getAnswerId())
                .withUpdateRequest(updateRequest)
                .withClass(Answer.class)
                .build();
        UpdateResponse response = elasticsearchOperations.update(updateQuery);
        return response.getResult().getLowercase();
    }
}
