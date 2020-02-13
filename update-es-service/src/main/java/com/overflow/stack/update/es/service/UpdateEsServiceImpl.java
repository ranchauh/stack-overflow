package com.overflow.stack.update.es.service;

import com.overflow.stack.es.enums.EsAction;
import com.overflow.stack.es.model.Answer;
import com.overflow.stack.es.model.Question;
import com.overflow.stack.es.service.AnswerService;
import com.overflow.stack.es.service.QuestionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateEsServiceImpl implements UpdateEsService{

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @SneakyThrows
    public void updateQuestion(Question question, EsAction esAction){
        if(esAction == EsAction.CREATE) {
            questionService.save(question);
        }else if(esAction == EsAction.UPDATE){
            questionService.update(question);
        }else if(esAction == EsAction.DELETE){
            questionService.deleteById(question.getQuestionId());
        }
    }

    @SneakyThrows
    public void updateAnswer(Answer answer, EsAction esAction){
        if(esAction == EsAction.CREATE) {
            answerService.save(answer);
        }else if(esAction == EsAction.UPDATE){
            answerService.update(answer);
        }else if(esAction == EsAction.DELETE){
            answerService.deleteById(answer.getQuestionId());
        }
    }
}
