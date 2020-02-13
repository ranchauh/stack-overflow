package com.overflow.stack.update.config;

import com.overflow.stack.update.serializer.ObjectSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value("${so.question.topic.name}")
    private String questionTopicName;

    @Value("${so.answer.topic.name}")
    private String answerTopicName;

    @Value("${so.question.topic.partitions:3}")
    private int questionTopicNumberOfPartition;

    @Value("${so.question.replica.factor:1}")
    private int questionTopicReplicaFactor;

    @Value("${so.answer.topic.partitions:3}")
    private int answerTopicNumberOfPartition;

    @Value("${so.answer.replica.factor:1}")
    private int answerTopicReplicaFactor;


    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props =
                new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                ObjectSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic soQuestionTopic() {
        return new NewTopic(questionTopicName, questionTopicNumberOfPartition, (short) questionTopicReplicaFactor);
    }

    @Bean
    public NewTopic soAnswerTopic() {
        return new NewTopic(answerTopicName, answerTopicNumberOfPartition, (short) answerTopicReplicaFactor);
    }

}
