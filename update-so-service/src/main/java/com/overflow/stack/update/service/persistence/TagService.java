package com.overflow.stack.update.service.persistence;

import com.overflow.stack.update.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TagService {

    Optional<Tag> find(long tagId);

    Tag save(long questionId, Tag tag);

    Tag update(long tagId, Tag tag);

    void delete(long tagId);

}
