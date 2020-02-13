package com.overflow.stack.update.controller;

import com.overflow.stack.es.model.Question;
import com.overflow.stack.update.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/v1/questions")
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Question saveQuestion(@RequestBody Question question){
        return questionService.save(question);
    }

    @PutMapping(value = "/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Question updateQuestion(@PathVariable String questionId,
                                   @RequestBody Question question){
        return questionService.update(question);
    }

    @DeleteMapping(value = "/{questionId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String deleteQuestion(@PathVariable String questionId){
        questionService.delete(questionId);
        return String.format("Question %s deleted successfully.",questionId);
    }

}
