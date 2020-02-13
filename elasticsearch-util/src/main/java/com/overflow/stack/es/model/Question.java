package com.overflow.stack.es.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
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
}
