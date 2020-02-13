package com.overflow.stack.es.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@NoArgsConstructor
@Document(indexName = "answers", type = "answer")
public class Answer {
    @Id
    private String answerId;

    @Field(type = FieldType.Text)
    private String questionId;

    @Field(type = FieldType.Text)
    private String answerText;

    @Field(type = FieldType.Text)
    private String parentAnswerId;

    @Field(type = FieldType.Long)
    private long voteCount;

    @Field(type = FieldType.Long)
    private long createTimestamp;

    @Field(type = FieldType.Long)
    private long updateTimestamp;

    @Field(type = FieldType.Text)
    private String postedBy;

    @Field(type = FieldType.Text)
    private List<String> imageUrls;

    @Field(type = FieldType.Text)
    private List<String> videoUrls;

    Answer(String answerId, String questionId, String answerText, String parentAnswerId, long voteCount, long createTimestamp, long updateTimestamp, String postedBy, List<String> imageUrls, List<String> videoUrls) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerText = answerText;
        this.parentAnswerId = parentAnswerId;
        this.voteCount = voteCount;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.postedBy = postedBy;
        this.imageUrls = imageUrls;
        this.videoUrls = videoUrls;
    }

    public static AnswerBuilder builder() {
        return new AnswerBuilder();
    }

    public static class AnswerBuilder {
        private String answerId;
        private String questionId;
        private String answerText;
        private String parentAnswerId;
        private long voteCount;
        private long createTimestamp;
        private long updateTimestamp;
        private String postedBy;
        private List<String> imageUrls;
        private List<String> videoUrls;

        AnswerBuilder() {
        }

        public AnswerBuilder answerId(String answerId) {
            this.answerId = answerId;
            return this;
        }

        public AnswerBuilder questionId(String questionId) {
            this.questionId = questionId;
            return this;
        }

        public AnswerBuilder answerText(String answerText) {
            this.answerText = answerText;
            return this;
        }

        public AnswerBuilder parentAnswerId(String parentAnswerId) {
            this.parentAnswerId = parentAnswerId;
            return this;
        }

        public AnswerBuilder voteCount(long voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public AnswerBuilder createTimestamp(long createTimestamp) {
            this.createTimestamp = createTimestamp;
            return this;
        }

        public AnswerBuilder updateTimestamp(long updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
            return this;
        }

        public AnswerBuilder postedBy(String postedBy) {
            this.postedBy = postedBy;
            return this;
        }

        public AnswerBuilder imageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
            return this;
        }

        public AnswerBuilder videoUrls(List<String> videoUrls) {
            this.videoUrls = videoUrls;
            return this;
        }

        public Answer build() {
            return new Answer(answerId, questionId, answerText, parentAnswerId, voteCount, createTimestamp, updateTimestamp, postedBy, imageUrls, videoUrls);
        }

        public String toString() {
            return "Answer.AnswerBuilder(answerId=" + this.answerId + ", questionId=" + this.questionId + ", answerText=" + this.answerText + ", parentAnswerId=" + this.parentAnswerId + ", voteCount=" + this.voteCount + ", createTimestamp=" + this.createTimestamp + ", updateTimestamp=" + this.updateTimestamp + ", postedBy=" + this.postedBy + ", imageUrls=" + this.imageUrls + ", videoUrls=" + this.videoUrls + ")";
        }
    }
}
