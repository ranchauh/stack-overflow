package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.RichContent;
import com.overflow.stack.update.repository.persistence.AnswerRepository;
import com.overflow.stack.update.repository.persistence.RichContentRepository;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RichContentServiceImpl implements RichContentService {

    @Autowired
    private RichContentRepository richContentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public Optional<RichContent> find(String contentId){
        return richContentRepository.findById(contentId);
    }

    @Override
    public List<RichContent> findByContentType(String contentType) {
        return richContentRepository.findRichContentByContentType(contentType);
    }

    public RichContent save(Long answerId,RichContent richContent){
        return answerRepository.findById(answerId)
                .map(answer -> {
                    richContent.setAnswer(answer);
                    return richContentRepository.save(richContent);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format("Answer id %s not found",answerId)));
    }

    public RichContent update(String contentId,RichContent richContent){
        return richContentRepository.findById(contentId)
                .map(rc -> {
                    rc.setContentName(richContent.getContentName());
                    rc.setContentLocation(richContent.getContentLocation());
                    return richContentRepository.save(rc);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
    }

    public void delete(String contentId){
        richContentRepository.deleteById(contentId);
    }

}
