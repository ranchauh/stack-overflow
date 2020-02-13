package com.overflow.stack.update.es.service;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaConsumerService {

    void receiveQuestion(@Payload String data,
                         @Headers MessageHeaders messageHeaders);

    void receiveAnswer(@Payload String data,
                              @Headers MessageHeaders messageHeaders);
}
