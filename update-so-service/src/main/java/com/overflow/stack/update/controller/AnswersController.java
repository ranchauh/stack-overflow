package com.overflow.stack.update.controller;

import com.overflow.stack.commons.enums.EsAction;
import com.overflow.stack.update.entity.Answer;
import com.overflow.stack.update.service.KafkaSenderService;
import com.overflow.stack.update.service.persistence.AnswerService;
import com.overflow.stack.update.service.persistence.RichContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping(value = "api/v1")
@RestController
public class AnswersController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private KafkaSenderService kafkaSenderService;

    @Autowired
    private RichContentService richContentService;

    @PostMapping(value = "/questions/{questionId}/answers",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Answer saveAnswer(@PathVariable Long questionId,
                             @RequestBody Answer answer){
        Answer answerSaved = answerService.save(questionId,answer);
        kafkaSenderService.sendAnswer(answerSaved, EsAction.CREATE);
        return answerSaved;
    }

    @PostMapping(value = "/answers/{parentAnswerId}/answers",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Answer saveAnswerOfAnswer(@PathVariable Long parentAnswerId,
                                     @RequestBody Answer answer){
        Answer answerSaved = answerService.saveChildAnswer(parentAnswerId,answer);
        kafkaSenderService.sendAnswer(answerSaved, EsAction.CREATE);
        return answerSaved;
    }

    @PutMapping(value = "/answers/{answerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer updateAnswer(@PathVariable Long answerId,
                                   @RequestBody Answer answer){
        Answer answerSaved = answerService.update(answerId,answer);
        kafkaSenderService.sendAnswer(answerSaved, EsAction.UPDATE);
        return answerSaved;
    }

    @DeleteMapping(value = "/answers/{answerId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String deleteAnswer(@PathVariable long answerId){
        return delAnswer(answerId);
    }

    @DeleteMapping(value = "/{parentAnswerId}/answers/{answerId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String deleteAnswerOfAnswer(@PathVariable long answerId){
        return delAnswer(answerId);
    }

    private String delAnswer(long answerId){
        answerService.delete(answerId);
        Optional<Answer> answer = answerService.find(answerId);
        if(answer.isPresent()){
            answerService.delete(answerId);
            kafkaSenderService.sendAnswer(answer.get(),EsAction.DELETE);
        }
        return String.format("Answer %s deleted successfully.",answerId);
    }

    @PostMapping(value = "/answers/{answerId}/votes")
    public Answer voteAnswer(@PathVariable Long answerId){
        Answer answerSaved = answerService.voteAnswer(answerId);
        kafkaSenderService.sendAnswer(answerSaved, EsAction.CREATE);
        return answerSaved;
    }
}
