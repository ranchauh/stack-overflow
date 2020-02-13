package com.overflow.stack.search.controller;

import com.overflow.stack.search.service.SearchAnswerService;
import com.overflow.stack.es.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping(value = "api/v1/answers")
@RestController
public class SearchAnswerController {

    @Autowired
    private SearchAnswerService searchAnswerService;

    @GetMapping(value = "/{answerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer getAnswerById(@PathVariable("answerId") String answerId){
        return searchAnswerService.getAnswerById(answerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{answerId}/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Answer> getAnswersByParentId(@PathVariable("answerId") String parentAnswerId,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "50") int size){
        return searchAnswerService.getAnswersByParentId(parentAnswerId, page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Answer> getAnswersByText(@RequestParam(value = "query", required = false) String query,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "size", required = false, defaultValue = "50") int size){
        return searchAnswerService.getAnswersByText(query,page, size);
    }

}
