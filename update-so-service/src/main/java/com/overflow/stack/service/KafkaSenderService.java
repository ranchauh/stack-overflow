package com.overflow.stack.service;

import com.overflow.stack.es.enums.EsAction;
import com.overflow.stack.es.model.Answer;
import com.overflow.stack.es.model.Question;

public interface KafkaSenderService {

    void sendQuestion(Question question, EsAction esAction);

    void sendAnswer(Answer answer, EsAction esAction);

}
