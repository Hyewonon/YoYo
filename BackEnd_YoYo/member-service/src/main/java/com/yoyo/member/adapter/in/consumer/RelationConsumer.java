package com.yoyo.member.adapter.in.consumer;

import com.yoyo.common.kafka.dto.IncreaseAmountDTO;
import com.yoyo.common.kafka.dto.TransactionDTO;
import com.yoyo.member.adapter.out.persistence.SpringDataMemberRepository;
import com.yoyo.member.adapter.out.persistence.SpringDataRelationRepository;
import com.yoyo.member.adapter.out.persistence.entity.RelationJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RelationConsumer {
    private final SpringDataRelationRepository relationRepository;
    private final SpringDataMemberRepository memberRepository;

    @KafkaListener(topics = "transaction-register-topic", concurrency = "3")
    public void setRelation(IncreaseAmountDTO amount) {
        Optional<RelationJpaEntity> entity = relationRepository.findByMember_MemberIdAndOppositeId(amount.getMemberId(), amount.getOppositeId());
        if (entity.isPresent()) {
            if (amount.isSend()) {
                entity.get().setTotalSentAmount(entity.get().getTotalSentAmount() + amount.getAmount());
            } else {
                entity.get().setTotalReceivedAmount(entity.get().getTotalReceivedAmount() + amount.getAmount());
            }
        } else {
            if (amount.isSend()) {
                RelationJpaEntity jpa = RelationJpaEntity.builder()
                        .oppositeId(amount.getOppositeId())
                        .totalSentAmount(amount.getAmount())
                        .member(memberRepository.findByMemberId(amount.getMemberId()))
                        .build();
                relationRepository.save(jpa);
            } else {
                RelationJpaEntity jpa = RelationJpaEntity.builder()
                        .oppositeId(amount.getOppositeId())
                        .totalReceivedAmount(amount.getAmount())
                        .member(memberRepository.findByMemberId(amount.getMemberId()))
                        .build();
                relationRepository.save(jpa);
            }
        }
    }
    @KafkaListener(topics = "payment-register", concurrency = "3")
    public void setPaymentRelation(TransactionDTO transactionDTO) {
        RelationJpaEntity jpaEntity = new RelationJpaEntity(

        );
    }
}
