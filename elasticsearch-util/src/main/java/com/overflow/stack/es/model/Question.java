package com.overflow.stack.es.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "questions", type = "question")
public class Question {
    @Id
    private String questionId;

    @Field(type = FieldType.Text)
    private String questionTitle;

    @Field(type = FieldType.Text)
    private String questionDescription;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @Field(type = FieldType.Long)
    private long voteCount;

    @Field(type = FieldType.Long)
    private long createTimestamp;

    @Field(type = FieldType.Long)
    private long updateTimestamp;

    @Field(type = FieldType.Text)
    private String postedBy;

    Question(String questionId, String questionTitle, String questionDescription, List<String> tags, long voteCount, long createTimestamp, long updateTimestamp, String postedBy) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionDescription = questionDescription;
        this.tags = tags;
        this.voteCount = voteCount;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.postedBy = postedBy;
    }

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public static class QuestionBuilder {
        private String questionId;
        private String questionTitle;
        private String questionDescription;
        private List<String> tags;
        private long voteCount;
        private long createTimestamp;
        private long updateTimestamp;
        private String postedBy;

        QuestionBuilder() {
        }

        public QuestionBuilder questionId(String questionId) {
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

        public QuestionBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public QuestionBuilder voteCount(long voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public QuestionBuilder createTimestamp(long createTimestamp) {
            this.createTimestamp = createTimestamp;
            return this;
        }

        public QuestionBuilder updateTimestamp(long updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
            return this;
        }

        public QuestionBuilder postedBy(String postedBy) {
            this.postedBy = postedBy;
            return this;
        }

        public Question build() {
            return new Question(questionId, questionTitle, questionDescription, tags, voteCount, createTimestamp, updateTimestamp, postedBy);
        }

        public String toString() {
            return "Question.QuestionBuilder(questionId=" + this.questionId + ", questionTitle=" + this.questionTitle + ", questionDescription=" + this.questionDescription + ", tags=" + this.tags + ", voteCount=" + this.voteCount + ", createTimestamp=" + this.createTimestamp + ", updateTimestamp=" + this.updateTimestamp + ", postedBy=" + this.postedBy + ")";
        }
    }
}
