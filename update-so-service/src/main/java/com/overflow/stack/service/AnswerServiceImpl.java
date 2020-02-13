package com.overflow.stack.service;

import com.overflow.stack.es.model.Answer;
import com.overflow.stack.update.repository.MongoAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private MongoAnswerRepository mongoAnswerRepository;

    public Optional<Answer> find(String answerId){
        return mongoAnswerRepository.findById(answerId);
    }

    public Answer save(Answer answer){
        return mongoAnswerRepository.insert(answer);
    }

    public Answer update(Answer answer){
        return mongoAnswerRepository.save(answer);
    }

    public void delete(String answerId){
        mongoAnswerRepository.deleteById(answerId);
    }

}
