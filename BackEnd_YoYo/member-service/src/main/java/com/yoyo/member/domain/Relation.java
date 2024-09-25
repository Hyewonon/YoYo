package com.yoyo.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Relation {
    private final Long relationId;
    private final Long oppositeId;
    private final String oppositeName;
    private final String relationType;
    private final String description;
    private final long totalReceivedAmount;
    private final long totalSentAmount;
    public static Relation generateRelation(RelationId relationId, OppositeId oppositeId, OppositeName oppositeName, RelationType relationType, Description description, TotalReceivedAmount totalReceivedAmount, TotalSenderAmount totalSenderAmount) {
        return new Relation(relationId.relationId,
                oppositeId.oppositeId,
                oppositeName.oppositeName,
                relationType.relationType,
                description.description,
                totalReceivedAmount.totalReceivedAmount,
                totalSenderAmount.totalSenderAmount);
    }

    @Value
    public static class RelationId {
        Long relationId;

        public RelationId(Long value) {
            this.relationId = value;
        }
    }

    @Value
    public static class OppositeId {
        Long oppositeId;

        public OppositeId(Long value) {
            this.oppositeId = value;
        }
    }
    @Value
    public static class OppositeName {
        String oppositeName;

        public OppositeName(String value) {
            this.oppositeName = value;
        }
    }
    @Value
    public static class RelationType {
        String relationType;

        public RelationType(String value) {
            this.relationType = value;
        }
    }
    @Value
    public static class Description {
        String description;

        public Description(String value) {
            this.description = value;
        }
    }
    @Value
    public static class TotalReceivedAmount {
        long totalReceivedAmount;

        public TotalReceivedAmount(long value) {
            this.totalReceivedAmount = value;
        }
    }

    @Value
    public static class TotalSenderAmount {
        long totalSenderAmount;

        public TotalSenderAmount(long value) {
            this.totalSenderAmount = value;
        }
    }
}
