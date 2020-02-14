package com.overflow.stack.update.service;

import com.overflow.stack.commons.enums.EsAction;
import com.overflow.stack.update.entity.Answer;
import com.overflow.stack.update.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaSenderServiceImpl implements KafkaSenderService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${so.question.topic.name}")
    private String questionTopicName;

    @Value("${so.answer.topic.name}")
    private String answerTopicName;

    public void sendQuestion(Question question, EsAction esAction) {
        this.updateQuestionBeforeSend(question);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(MessageBuilder.withPayload(question)
                .setHeader(KafkaHeaders.TOPIC, questionTopicName)
                .setHeader("esAction",esAction.name())
                .build());
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while sending question to kafka");
        }
    }

    public void sendAnswer(Answer answer, EsAction esAction) {
        this.updateAnswerBeforeSend(answer);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(MessageBuilder.withPayload(answer)
                .setHeader(KafkaHeaders.TOPIC, answerTopicName)
                .setHeader("esAction",esAction.name())
                .build());
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while sending answer to kafka");
        }
    }

    private Answer updateAnswerBeforeSend(Answer answer){
        answer.setQuestionId(answer.getQuestion().getQuestionId());
        answer.setPostedBy(answer.getUser().getUserId());
        Answer parentAnswer = answer.getParentAnswer();
        if(Objects.nonNull(parentAnswer)){
            answer.setParentAnswerId(parentAnswer.getAnswerId());
        }
        return answer;
    }

    private Question updateQuestionBeforeSend(Question question){
        question.setPostedBy(question.getUser().getUserId());
        return question;
    }

}
