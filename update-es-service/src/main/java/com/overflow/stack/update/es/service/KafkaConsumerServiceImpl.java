package com.overflow.stack.update.es.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overflow.stack.commons.enums.EsAction;
import com.overflow.stack.commons.model.Answer;
import com.overflow.stack.commons.model.Question;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private UpdateEsService updateEsService;

    @KafkaListener(topics = "${so.question.topic.name}")
    public void receiveQuestion(@Payload String data,
                                @Headers MessageHeaders messageHeaders) {
        EsAction esAction = this.getEsAction(messageHeaders.get("esAction"));
        log.info("Question message action is: {}", esAction);
        Question question = this.getObject(data,Question.class);
        this.updateEsService.updateQuestion(question, esAction);
    }

    @KafkaListener(topics = "${so.answer.topic.name}")
    public void receiveAnswer(@Payload String data,
                              @Headers MessageHeaders messageHeaders) {
        EsAction esAction = this.getEsAction(messageHeaders.get("esAction"));
        log.info("Answer message action is: {}", esAction);
        Answer answer = this.getObject(data,Answer.class);
        this.updateEsService.updateAnswer(answer,esAction);
    }

    private EsAction getEsAction(Object esAction){
        return EsAction.valueOf(String.valueOf(esAction));
    }

    @SneakyThrows
    private <T> T getObject(String json, Class<T> targetType){
        T object;
        try {
            object = OBJECT_MAPPER.readValue(json, targetType);
        } catch (Exception e) {
            log.error("Error while receiving kafka message");
            throw e;
        }
        return object;
    }

}
