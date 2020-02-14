package com.overflow.stack.update.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.overflow.stack.user.service.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "TAG")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private String tagId;

    @Column(nullable = false)
    private String tagName;

    private String tagDescription;

    @Transient
    private Long questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @CreationTimestamp
    private Timestamp createTimestamp;

    @UpdateTimestamp
    private Timestamp updateTimestamp;

    @Transient
    private String createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User user;

    Tag(String tagId, String tagName, String tagDescription, Long questionId, Question question, Timestamp createTimestamp, Timestamp updateTimestamp, String createdBy, User user) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
        this.questionId = questionId;
        this.question = question;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.createdBy = createdBy;
        this.user = user;
    }

    public static TagBuilder builder() {
        return new TagBuilder();
    }

    public static class TagBuilder {
        private String tagId;
        private String tagName;
        private String tagDescription;
        private Long questionId;
        private Question question;
        private Timestamp createTimestamp;
        private Timestamp updateTimestamp;
        private String createdBy;
        private User user;

        TagBuilder() {
        }

        public TagBuilder tagId(String tagId) {
            this.tagId = tagId;
            return this;
        }

        public TagBuilder tagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public TagBuilder tagDescription(String tagDescription) {
            this.tagDescription = tagDescription;
            return this;
        }

        public TagBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public TagBuilder question(Question question) {
            this.question = question;
            return this;
        }

        public TagBuilder createTimestamp(Timestamp createTimestamp) {
            this.createTimestamp = createTimestamp;
            return this;
        }

        public TagBuilder updateTimestamp(Timestamp updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
            return this;
        }

        public TagBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public TagBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Tag build() {
            return new Tag(tagId, tagName, tagDescription, questionId, question, createTimestamp, updateTimestamp, createdBy, user);
        }
    }
}
