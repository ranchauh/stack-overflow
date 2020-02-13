package com.overflow.stack.update.service;

import com.overflow.stack.es.model.Question;
import com.overflow.stack.update.repository.MongoQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private MongoQuestionRepository mongoQuestionRepository;

    public Optional<Question> find(String questionId){
        return mongoQuestionRepository.findById(questionId);
    }

    public Question save(Question question){
        return mongoQuestionRepository.insert(question);
    }

    public Question update(Question question){
        return mongoQuestionRepository.save(question);
    }

    public void delete(String questionId){
        mongoQuestionRepository.deleteById(questionId);
    }

}
