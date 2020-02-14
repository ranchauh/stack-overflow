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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ANSWER")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class Answer {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private Long answerId;

    @Transient
    private Long questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    @Column(nullable = false)
    private String answerText;

    @Transient
    private Long parentAnswerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_answer_id")
    @JsonIgnore
    private Answer parentAnswer;

    private Long voteCount;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @JsonIgnore
    private List<RichContent> richContents;

    @Transient
    private List<String> imageIds;

    @Transient
    private List<String> videoIds;

    Answer(Long answerId, Long questionId, Question question, String answerText, Long parentAnswerId, Answer parentAnswer, Long voteCount, Timestamp createTimestamp, Timestamp updateTimestamp, String postedBy, User user, List<RichContent> richContents, List<String> imageIds, List<String> videoIds) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.question = question;
        this.answerText = answerText;
        this.parentAnswerId = parentAnswerId;
        this.parentAnswer = parentAnswer;
        this.voteCount = voteCount;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.postedBy = postedBy;
        this.user = user;
        this.richContents = richContents;
        this.imageIds = imageIds;
        this.videoIds = videoIds;
    }

    public static AnswerBuilder builder() {
        return new AnswerBuilder();
    }

    public static class AnswerBuilder {
        private Long answerId;
        private Long questionId;
        private Question question;
        private String answerText;
        private Long parentAnswerId;
        private Answer parentAnswer;
        private Long voteCount;
        private Timestamp createTimestamp;
        private Timestamp updateTimestamp;
        private String postedBy;
        private User user;
        private List<RichContent> richContents;
        private List<String> imageIds;
        private List<String> videoIds;

        AnswerBuilder() {
        }

        public AnswerBuilder answerId(Long answerId) {
            this.answerId = answerId;
            return this;
        }

        public AnswerBuilder questionId(Long questionId) {
            this.questionId = questionId;
            return this;
        }

        public AnswerBuilder question(Question question) {
            this.question = question;
            return this;
        }

        public AnswerBuilder answerText(String answerText) {
            this.answerText = answerText;
            return this;
        }

        public AnswerBuilder parentAnswerId(Long parentAnswerId) {
            this.parentAnswerId = parentAnswerId;
            return this;
        }

        public AnswerBuilder parentAnswer(Answer parentAnswer) {
            this.parentAnswer = parentAnswer;
            return this;
        }

        public AnswerBuilder voteCount(Long voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public AnswerBuilder createTimestamp(Timestamp createTimestamp) {
            this.createTimestamp = createTimestamp;
            return this;
        }

        public AnswerBuilder updateTimestamp(Timestamp updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
            return this;
        }

        public AnswerBuilder postedBy(String postedBy) {
            this.postedBy = postedBy;
            return this;
        }

        public AnswerBuilder user(User user) {
            this.user = user;
            return this;
        }

        public AnswerBuilder richContents(List<RichContent> richContents) {
            this.richContents = richContents;
            return this;
        }

        public AnswerBuilder imageIds(List<String> imageIds) {
            this.imageIds = imageIds;
            return this;
        }

        public AnswerBuilder videoIds(List<String> videoIds) {
            this.videoIds = videoIds;
            return this;
        }

        public Answer build() {
            return new Answer(answerId, questionId, question, answerText, parentAnswerId, parentAnswer, voteCount, createTimestamp, updateTimestamp, postedBy, user, richContents, imageIds, videoIds);
        }

        public String toString() {
            return "Answer.AnswerBuilder(answerId=" + this.answerId + ", questionId=" + this.questionId + ", question=" + this.question + ", answerText=" + this.answerText + ", parentAnswerId=" + this.parentAnswerId + ", parentAnswer=" + this.parentAnswer + ", voteCount=" + this.voteCount + ", createTimestamp=" + this.createTimestamp + ", updateTimestamp=" + this.updateTimestamp + ", postedBy=" + this.postedBy + ", user=" + this.user + ", richContents=" + this.richContents + ", imageIds=" + this.imageIds + ", videoIds=" + this.videoIds + ")";
        }
    }
}
