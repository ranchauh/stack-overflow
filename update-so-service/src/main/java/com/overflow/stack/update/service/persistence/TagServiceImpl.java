package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.Tag;
import com.overflow.stack.update.repository.persistence.QuestionRepository;
import com.overflow.stack.update.repository.persistence.TagRepository;
import com.overflow.stack.user.service.entity.User;
import com.overflow.stack.user.service.repository.UserRepository;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Optional<Tag> find(long tagId){
        return tagRepository.findById(tagId);
    }

    public Tag save(long questionId,Tag tag){
        User user = userRepository.findById(tag.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User id %s does not exist",tag.getCreatedBy())));
        return questionRepository.findById(questionId)
                .map(q -> {
                    tag.setUser(user);
                    tag.setQuestion(q);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Question id %s does not exist",questionId)));
    }

    public Tag update(long tagId, Tag tag){
        return tagRepository.findById(tagId)
                .map(t -> {
                    t.setTagName(tag.getTagName());
                    t.setTagDescription(tag.getTagDescription());
                    return tagRepository.save(t);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Tag id %s not found.",tagId)));
    }

    public void delete(long tagId){
        tagRepository.deleteById(tagId);
    }

}
