package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.Question;
import com.overflow.stack.update.repository.persistence.QuestionRepository;
import com.overflow.stack.user.service.repository.UserRepository;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Question> find(long questionId){
        return questionRepository.findById(questionId);
    }

    public Question save(Question question){
        return userRepository.findById(question.getPostedBy())
                .map(user -> {
                    question.setVoteCount(0L);
                    question.setUser(user);
                    return questionRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with id %s does not exists.",question.getPostedBy())));
    }

    public Question update(long questionId,Question question){
        return questionRepository.findById(questionId)
                .map(q -> {
                    q.setQuestionTitle(question.getQuestionTitle());
                    q.setQuestionDescription(question.getQuestionDescription());
                    return questionRepository.save(q);
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Question id %s does not exist",questionId)));
    }

    public void delete(long questionId){
        questionRepository.deleteById(questionId);
    }

    public Question voteQuestion(long questionId){
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.voteQuestion(questionId);
                    return questionRepository.getOne(questionId);
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Question id %s does not exist",questionId)));

    }
}
