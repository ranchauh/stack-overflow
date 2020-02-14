package com.overflow.stack.update.es.service;

import com.overflow.stack.commons.enums.EsAction;
import com.overflow.stack.commons.model.Answer;
import com.overflow.stack.commons.model.Question;
import com.overflow.stack.commons.service.EsAnswerService;
import com.overflow.stack.commons.service.EsQuestionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateEsServiceImpl implements UpdateEsService{

    @Autowired
    private EsQuestionService esQuestionService;

    @Autowired
    private EsAnswerService esAnswerService;

    @SneakyThrows
    public void updateQuestion(Question question, EsAction esAction){
        if(esAction == EsAction.CREATE) {
            esQuestionService.save(question);
        }else if(esAction == EsAction.UPDATE){
            esQuestionService.update(question);
        }else if(esAction == EsAction.DELETE){
            esQuestionService.deleteById(question.getQuestionId());
        }
    }

    @SneakyThrows
    public void updateAnswer(Answer answer, EsAction esAction){
        if(esAction == EsAction.CREATE) {
            esAnswerService.save(answer);
        }else if(esAction == EsAction.UPDATE){
            esAnswerService.update(answer);
        }else if(esAction == EsAction.DELETE){
            esAnswerService.deleteById(answer.getQuestionId());
        }
    }
}
