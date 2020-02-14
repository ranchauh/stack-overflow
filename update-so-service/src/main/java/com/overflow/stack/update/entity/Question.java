package com.overflow.stack.update.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.overflow.stack.update.service.persistence.StringListConverter;
import com.overflow.stack.user.service.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
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
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "QUESTION")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class Question {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private Long questionId;

    @Column(nullable = false)
    private String questionTitle;

    private String questionDescription;

    private Long voteCount;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> tags;

    @CreationTimestamp
    private Timestamp createTimestamp;

    @UpdateTimestamp
    private Timestamp updateTimestamp;

    @Transient
    private String postedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by")
    @JsonIgnore
    private User user;

    Question(Long questionId, String questionTitle, String questionDescription, Long voteCount, List<String> tags, Timestamp createTimestamp, Timestamp updateTimestamp, String postedBy, User user) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionDescription = questionDescription;
        this.voteCount = voteCount;
        this.tags = tags;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.postedBy = postedBy;
        this.user = user;
    }

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public static class QuestionBuilder {
        private Long questionId;
        private String questionTitle;
        private String questionDescription;
        private Long voteCount;
        private List<String> tags;
        private Timestamp createTimestamp;
        private Timestamp updateTimestamp;
        private String postedBy;
        private User user;

        QuestionBuilder() {
        }

        public QuestionBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public QuestionBuilder questionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
            return this;
        }

        public QuestionBuilder questionDescription(String questionDescription) {
            this.questionDescription = questionDescription;
            return this;
        }

        public QuestionBuilder voteCount(Long voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public QuestionBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public QuestionBuilder createTimestamp(Timestamp createTimestamp) {
            this.createTimestamp = createTimestamp;
            return this;
        }

        public QuestionBuilder updateTimestamp(Timestamp updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
            return this;
        }

        public QuestionBuilder postedBy(String postedBy) {
            this.postedBy = postedBy;
            return this;
        }

        public QuestionBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Question build() {
            return new Question(questionId, questionTitle, questionDescription, voteCount, tags, createTimestamp, updateTimestamp, postedBy, user);
        }

        public String toString() {
            return "Question.QuestionBuilder(questionId=" + this.questionId + ", questionTitle=" + this.questionTitle + ", questionDescription=" + this.questionDescription + ", voteCount=" + this.voteCount + ", tags=" + this.tags + ", createTimestamp=" + this.createTimestamp + ", updateTimestamp=" + this.updateTimestamp + ", postedBy=" + this.postedBy + ", user=" + this.user + ")";
        }
    }
}
