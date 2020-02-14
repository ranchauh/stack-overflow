package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.RichContent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RichContentService {

    Optional<RichContent> find(String contentId);

    List<RichContent> findByContentType(String contentType);

    RichContent save(Long answerId, RichContent richContent);

    RichContent update(String contentId,RichContent richContent);

    void delete(String contentId);

}
