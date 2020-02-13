package com.overflow.stack.search.service;

import com.overflow.stack.es.model.Answer;
import com.overflow.stack.es.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchAnswerService {

    @Autowired
    private AnswerService answerService;

    public Optional<Answer> getAnswerById(String answerId){
        return answerService.findById(answerId);
    }

    public Page<Answer> getAnswersByParentId(String parentAnswerId,int page,int size){
        return answerService.findByParentAnswerId(parentAnswerId, PageRequest.of(page,size));
    }

    public Page<Answer> getAnswersByText(String searchText, int page, int size){
        return answerService.findByText(searchText,PageRequest.of(page,size));
    }

    public Page<Answer> getAnswerByQuestionId(String questionId, int page, int size){
        return answerService.findByQuestionId(questionId,PageRequest.of(page,size));
    }
}
