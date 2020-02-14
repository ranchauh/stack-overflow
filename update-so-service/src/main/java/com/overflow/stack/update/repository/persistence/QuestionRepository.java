package com.overflow.stack.update.repository.persistence;

import com.overflow.stack.update.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Question q SET q.voteCount=q.voteCount+1 WHERE q.questionId=:questionId")
    int voteQuestion(@Param("questionId") Long questionId);
}
