package com.overflow.stack.update.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class ObjectSerializer implements Serializer<Object> {

    @SneakyThrows
    @Override
    public byte[] serialize(String s, Object object) {
        byte[] retVal;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(object).getBytes();
        } catch (Exception e) {
            log.error("Error while serializing question/answer");
            throw e;
        }
        return retVal;
    }
}
