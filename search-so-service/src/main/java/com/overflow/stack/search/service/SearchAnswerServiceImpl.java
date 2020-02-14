package com.overflow.stack.search.service;

import com.overflow.stack.commons.model.Answer;
import com.overflow.stack.commons.service.EsAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchAnswerServiceImpl implements SearchAnswerService{

    @Autowired
    private EsAnswerService esAnswerService;

    public Optional<Answer> getAnswerById(String answerId){
        return esAnswerService.findById(answerId);
    }

    public Page<Answer> getAnswersByParentId(String parentAnswerId,int page,int size){
        return esAnswerService.findByParentAnswerId(parentAnswerId, PageRequest.of(page,size));
    }

    public Page<Answer> getAnswersByText(String searchText, int page, int size){
        return esAnswerService.findByText(searchText,PageRequest.of(page,size));
    }

    public Page<Answer> getAnswerByQuestionId(String questionId, int page, int size){
        return esAnswerService.findByQuestionId(questionId,PageRequest.of(page,size));
    }
}
