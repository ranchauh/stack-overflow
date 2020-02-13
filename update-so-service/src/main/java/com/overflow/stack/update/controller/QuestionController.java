package com.overflow.stack.update.controller;

import com.overflow.stack.es.enums.EsAction;
import com.overflow.stack.es.model.Question;
import com.overflow.stack.service.KafkaSenderService;
import com.overflow.stack.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping(value = "api/v1/questions")
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private KafkaSenderService kafkaSenderService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Question saveQuestion(@RequestBody Question question){
        Question questionSaved = questionService.save(question);
        kafkaSenderService.sendQuestion(questionSaved,EsAction.CREATE);
        return questionSaved;
    }

    @PutMapping(value = "/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Question updateQuestion(@PathVariable String questionId,
                                   @RequestBody Question question){
        Question questionUpdated = questionService.update(question);
        kafkaSenderService.sendQuestion(questionUpdated, EsAction.UPDATE);
        return questionUpdated;
    }

    @DeleteMapping(value = "/{questionId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String deleteQuestion(@PathVariable String questionId){
        Optional<Question> question = questionService.find(questionId);
        if(question.isPresent()){
            questionService.delete(questionId);
            kafkaSenderService.sendQuestion(question.get(),EsAction.DELETE);
        }
        return String.format("Question %s deleted successfully.",questionId);
    }

}
