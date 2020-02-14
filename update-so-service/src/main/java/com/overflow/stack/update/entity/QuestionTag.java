package com.overflow.stack.update.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "QUESTION_TAG")
@IdClass(QuestionTagId.class)
public class QuestionTag {

    @Id
    private String questionId;

    @Id
    private String tagName;

    @CreationTimestamp
    private Timestamp createTimestamp;

    @UpdateTimestamp
    private Timestamp updateTimestamp;

}

@Data
class QuestionTagId implements Serializable {
    private String questionId;
    private String tagName;
}
