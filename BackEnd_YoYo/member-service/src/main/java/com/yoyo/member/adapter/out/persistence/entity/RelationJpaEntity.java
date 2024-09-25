package com.yoyo.member.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "relation")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class RelationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private MemberJpaEntity member;
    private Long oppositeId;
    private String oppositeName;
    @Enumerated(EnumType.STRING)
    private RelationType relationType;
    private String description;
    @Setter
    private long totalReceivedAmount;
    @Setter
    private long totalSentAmount;
    private boolean isRegister;

    public RelationJpaEntity(String oppositeName, Long receiverId, String receiverName, Long eventId, String title, long amount, String memo) {

    }
}
