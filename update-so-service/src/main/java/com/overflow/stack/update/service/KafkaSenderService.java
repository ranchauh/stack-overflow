package com.overflow.stack.update.service;

import com.overflow.stack.commons.enums.EsAction;
import com.overflow.stack.update.entity.Answer;
import com.overflow.stack.update.entity.Question;

public interface KafkaSenderService {

    void sendQuestion(Question question, EsAction esAction);

    void sendAnswer(Answer answer, EsAction esAction);

}
