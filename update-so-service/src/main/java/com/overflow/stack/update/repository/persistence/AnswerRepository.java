package com.overflow.stack.update.repository.persistence;

import com.overflow.stack.update.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Answer a SET a.voteCount=a.voteCount+1 WHERE a.answerId=:answerId")
    int voteAnswer(@Param("answerId") Long answerId);
}
