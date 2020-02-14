package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.Answer;
import com.overflow.stack.update.entity.RichContent;
import com.overflow.stack.update.repository.persistence.AnswerRepository;
import com.overflow.stack.update.repository.persistence.QuestionRepository;
import com.overflow.stack.user.service.entity.User;
import com.overflow.stack.user.service.repository.UserRepository;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Answer> find(long answerId){
        return answerRepository.findById(answerId);
    }

    public Answer save(long questionId,Answer answer){
        User user = userRepository.findById(answer.getPostedBy())
                .orElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %s does not exists.",answer.getPostedBy())));

        return questionRepository.findById(questionId)
                .map(question -> {
                    answer.setUser(user);
                    answer.setQuestion(question);
                    answer.setVoteCount(0L);
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Question id %s does not exist",questionId)));
    }


    @Override
    public Answer saveChildAnswer(Long parentAnswerId, Answer answer) {
        return answerRepository.findById(parentAnswerId)
                .map(pa -> {
                    answer.setVoteCount(0L);
                    answer.setParentAnswer(pa);
                    answer.setUser(userRepository.getOne(pa.getUser().getUserId()));
                    answer.setQuestion(questionRepository.getOne(pa.getQuestion().getQuestionId()));
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Parent answer id %s does not exist", parentAnswerId)));
    }

    public Answer update(long answerId,Answer answer){
        return answerRepository.findById(answerId)
                .map(ans -> {
                    ans.setAnswerText(answer.getAnswerText());
                    ans =  answerRepository.save(ans);
                    List<RichContent> richContents = ans.getRichContents();
                    if(!CollectionUtils.isEmpty(richContents)){
                        ans.setImageIds(filterRichContent(richContents,"IMAGE"));
                        ans.setVideoIds(filterRichContent(richContents,"VIDEO"));
                    }
                    return ans;
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Answer id %s does not exist",answerId)));
    }

    public void delete(long answerId){
        answerRepository.deleteById(answerId);
    }

    @Override
    public Answer voteAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.voteAnswer(answerId);
                    return  answerRepository.getOne(answerId);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Answer id %s does not exist",answerId)));
    }



    private List<String> filterRichContent(List<RichContent> richContents,String contentType){
        return richContents.stream()
                .filter(rc -> contentType.equals(rc.getContentType()))
                .map(RichContent::getContentName).collect(Collectors.toList());
    }


}
