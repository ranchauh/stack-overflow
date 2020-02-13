package com.overflow.stack.es.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
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
}
