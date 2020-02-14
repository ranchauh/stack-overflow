package com.overflow.stack.update.es.service;

import com.overflow.stack.commons.enums.EsAction;
import com.overflow.stack.commons.model.Answer;
import com.overflow.stack.commons.model.Question;

public interface UpdateEsService {

    void updateQuestion(Question question, EsAction esAction);

    void updateAnswer(Answer answer, EsAction esAction);

}
