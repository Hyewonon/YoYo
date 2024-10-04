package com.yoyo.member.domain.relation.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelationProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    private final String SEND_TRANSACTION_SELF_RELATION_TOPIC = "send-transaction-self-relation-topic";
    private final String SEND_RELATION_ID_LIST = "send-relation-id-list";

    public void sendMemberTag(MemberTagDTO response) {
        kafkaTemplate.send("notification-tag-topic", response);
    }

    /**
     * 요요 거래내역 직접 등록 시, 친구 관계 수정 후 상대 회원 여부 반환
     * */
    public void sendTransactionSelf(TransactionSelfRelationDTO.ResponseFromMember response) {
        kafkaTemplate.send(SEND_TRANSACTION_SELF_RELATION_TOPIC, response);
    }

    public void sendRelationIds(RelationResponseDTO response){
        kafkaTemplate.send(SEND_RELATION_ID_LIST, response);
    }

    public void sendUpdateTransactionRelationType(UpdateTransactionRelationTypeDTO request) {
        kafkaTemplate.send("update-transaction-relation-type-topic", request);
    }

    public void sendResultMatch(TransactionDTO.MatchRelation response) {
        kafkaTemplate.send("result-match-topic", response);
    }
}
