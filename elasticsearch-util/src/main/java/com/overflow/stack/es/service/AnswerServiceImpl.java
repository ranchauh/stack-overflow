package com.overflow.stack.es.service;

import com.overflow.stack.es.model.Answer;
import com.overflow.stack.es.repository.AnswerRepository;
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
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void deleteById(String answerId){
        answerRepository.deleteById(answerId);
    }

    @Override
    public Page<Answer> findByText(String searchText, Pageable pageable) {
        return answerRepository.findByText(searchText,pageable);
    }

    @Override
    public Page<Answer> findLatest(Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

    @Override
    public Page<Answer> findByParentAnswerId(String parentAnswerId, Pageable pageable) {
        return answerRepository.findByParentAnswerId(parentAnswerId,pageable);
    }

    @Override
    public Page<Answer> findByQuestionId(String questionId, Pageable pageable) {
        return answerRepository.findByQuestionId(questionId,pageable);
    }

    @Override
    public Optional<Answer> findById(String answerId) {
        return answerRepository.findById(answerId);
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
                        .field("imageUrls",answer.getImageUrls())
                        .field("videoUrls",answer.getVideoUrls())
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
