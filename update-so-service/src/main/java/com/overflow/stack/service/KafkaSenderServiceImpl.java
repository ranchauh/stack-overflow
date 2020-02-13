package com.overflow.stack.service;

import com.overflow.stack.es.enums.EsAction;
import com.overflow.stack.es.model.Answer;
import com.overflow.stack.es.model.Question;
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

}
