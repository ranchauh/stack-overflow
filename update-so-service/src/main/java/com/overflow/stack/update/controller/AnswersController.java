package com.overflow.stack.update.controller;

import com.overflow.stack.es.enums.EsAction;
import com.overflow.stack.es.model.Answer;
import com.overflow.stack.service.AnswerService;
import com.overflow.stack.service.KafkaSenderService;
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

@RequestMapping(value = "api/v1/answers")
@RestController
public class AnswersController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private KafkaSenderService kafkaSenderService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer saveAnswer(@RequestBody Answer answer){
        Answer answerSaved = answerService.save(answer);
        kafkaSenderService.sendAnswer(answerSaved, EsAction.CREATE);
        return answerSaved;
    }

    @PutMapping(value = "/{answerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer updateAnswer(@PathVariable String answerId,
                                   @RequestBody Answer answer){
        Answer answerSaved = answerService.update(answer);
        kafkaSenderService.sendAnswer(answerSaved, EsAction.UPDATE);
        return answerSaved;
    }

    @DeleteMapping(value = "/{answerId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String deleteAnswer(@PathVariable String answerId){
        answerService.delete(answerId);
        Optional<Answer> answer = answerService.find(answerId);
        if(answer.isPresent()){
            answerService.delete(answerId);
            kafkaSenderService.sendAnswer(answer.get(),EsAction.DELETE);
        }
        return String.format("Answer %s deleted successfully.",answerId);
    }

}
