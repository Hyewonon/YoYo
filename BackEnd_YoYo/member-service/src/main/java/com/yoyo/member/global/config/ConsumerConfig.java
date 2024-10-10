package com.yoyo.member.global.config;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.KafkaUtils;
import com.yoyo.common.kafka.dto.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class ConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                  ErrorHandlingDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                  ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.TYPE_MAPPINGS, KafkaUtils
                .getJsonTypeMappingInfo(IncreaseAmountDTO.class,
                                        TransactionDTO.class,
                                        MemberRequestDTO.class,
                                        PayInfoRequestToTransactionDTO.class,
                                        MemberTagDTO.class,
                                        TransactionSelfRelationDTO.RequestToMember.class,
                                        PaymentDTO.class,
                                        RelationDTO.Request.class,
                                        RelationResponseDTO.class,
                                        TransactionDTO.MatchRelation.class,
                                        UpdateRelationDTO.Request.class,
                                        PushTokenDTO.class,
                                        OcrRegister.OcrList.class,
                                        PayInfoRequestToMemberDTO.class
                ));

        return props;
    }

    @Bean
    public ConsumerFactory<String, KafkaJson> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaJson> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaJson> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
