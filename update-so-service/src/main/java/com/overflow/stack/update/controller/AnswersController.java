package com.overflow.stack.update.controller;

import com.overflow.stack.es.model.Answer;
import com.overflow.stack.update.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/v1/answers")
@RestController
public class AnswersController {

    @Autowired
    private AnswerService answerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer saveAnswer(@RequestBody Answer answer){
        return answerService.save(answer);
    }

    @PutMapping(value = "/{answerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer updateAnswer(@PathVariable String answerId,
                                   @RequestBody Answer answer){
        return answerService.update(answer);
    }

    @DeleteMapping(value = "/{answerId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String deleteAnswer(@PathVariable String answerId){
        answerService.delete(answerId);
        return String.format("Answer %s deleted successfully.",answerId);
    }

}
