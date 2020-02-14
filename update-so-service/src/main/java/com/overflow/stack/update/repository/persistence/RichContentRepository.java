package com.overflow.stack.update.repository.persistence;

import com.overflow.stack.update.entity.RichContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RichContentRepository extends JpaRepository<RichContent,String> {
    List<RichContent> findRichContentByContentType(String contentType);
}
