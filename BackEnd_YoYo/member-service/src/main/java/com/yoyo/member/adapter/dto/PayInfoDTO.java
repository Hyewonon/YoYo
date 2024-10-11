package com.yoyo.member.adapter.dto;

import com.yoyo.member.adapter.out.persistence.entity.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PayInfoDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private Long senderId;
        private Long receiverId;
        private Long amount;

        public static RelationJpaEntity toNewEntity(MemberJpaEntity member, Long oppositeId) {
            return RelationJpaEntity.builder()
                                    .member(member)
                                    .oppositeId(oppositeId)
                                    .relationType(RelationType.NONE)
                                    .description("")
                                    .totalReceivedAmount(0L)
                                    .totalSentAmount(0L)
                                    .build();
        }
    }
}
