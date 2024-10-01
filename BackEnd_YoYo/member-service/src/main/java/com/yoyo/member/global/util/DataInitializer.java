package com.yoyo.member.global.util;

import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.relation.repository.RelationRepository;
import com.yoyo.member.entity.Member;
import com.yoyo.member.entity.Relation;
import com.yoyo.member.entity.RelationType;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (memberRepository.count() == 0) {
        List<Member> members = generateDummyMembers();
        List<Relation> relations = generateDummyRelations(members);
        memberRepository.saveAll(members);
        relationRepository.saveAll(relations);
        }
    }

    private List<Member> generateDummyMembers() {
        List<Member> members = new ArrayList<>();
        Member ckl = Member.builder()
                .name("최광림")
                .phoneNumber("01054513115")
                .password(BCrypt.hashpw("Dydy1234!", BCrypt.gensalt()))
                .birthDay(LocalDate.of(1997, 4, 19))
                .isValid(true)
                .build();
        Member kht = Member.builder()
                .name("김현태")
                .phoneNumber("01049431481")
                .password(BCrypt.hashpw("Dydy1234!", BCrypt.gensalt()))
                .birthDay(LocalDate.of(1998, 9, 11))
                .build();
        Member lcj = Member.builder()
                .name("이찬진")
                .phoneNumber("01073391553")
                .password(BCrypt.hashpw("Dydy1234!", BCrypt.gensalt()))
                .birthDay(LocalDate.of(1998, 1, 19))
                .build();
        Member jhw = Member.builder()
                .name("장혜원")
                .phoneNumber("01096337327")
                .password(BCrypt.hashpw("Dydy1234!", BCrypt.gensalt()))
                .birthDay(LocalDate.of(1998, 1, 19))
                .build();
        Member sjk = Member.builder()
                .name("서진경")
                .phoneNumber("01029086212")
                .password(BCrypt.hashpw("Dydy1234!", BCrypt.gensalt()))
                .birthDay(LocalDate.of(1998, 1, 19))
                .build();

        members.add(ckl);
        members.add(kht);
        members.add(lcj);
        members.add(jhw);
        members.add(sjk);
        return members;
    }

    private List<Relation> generateDummyRelations(List<Member> members) {
        List<Relation> relations = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            for (int j = 0; j < members.size(); j++) {
                if (i != j) {
                    Relation relation = Relation.builder()
                            .member(members.get(i))
                            .oppositeId(members.get(j).getMemberId())
                            .oppositeName(members.get(j).getName())
                            .relationType(RelationType.FRIEND)
                            .description("친구 " + members.get(i).getName() + " <-> " + members.get(j).getName())
                            .totalReceivedAmount(10000L * (j + 1))
                            .totalSentAmount(50000L * (i + 1))
                            .isMember(true)
                            .build();
                    relations.add(relation);
                    members.get(i).getRelations().add(relation);
                }
            }
        }
        Member unknown = Member.builder()
                .name("김싸피")
                .phoneNumber("01012345678")
                .password(BCrypt.hashpw("Dydy1234!", BCrypt.gensalt()))
                .birthDay(LocalDate.of(2000, 1, 19))
                .build();
        Member unknown2 = Member.builder()
                .name("이택근")
                .phoneNumber("01087654312")
                .password(BCrypt.hashpw("Dydy1234!", BCrypt.gensalt()))
                .birthDay(LocalDate.of(2004, 1, 19))
                .build();
        members.add(unknown2);
        members.add(unknown);
        Relation relation1 = Relation.builder()
                .member(members.get(1))
                .oppositeId(unknown.getMemberId())
                .oppositeName(unknown.getName())
                .relationType(RelationType.NONE)
                .description("누구세요")
                .totalReceivedAmount(20000L)
                .totalSentAmount(0L)
                .isMember(false)
                .build();
        Relation relation2 = Relation.builder()
                .member(members.get(1))
                .oppositeId(unknown2.getMemberId())
                .oppositeName(unknown2.getName())
                .relationType(RelationType.NONE)
                .description("WHO?")
                .totalReceivedAmount(50000L)
                .totalSentAmount(0L)
                .isMember(false)
                .build();
        Relation relation3 = Relation.builder()
                .member(members.get(2))
                .oppositeId(unknown.getMemberId())
                .oppositeName(unknown.getName())
                .relationType(RelationType.NONE)
                .description("누구슈?")
                .totalReceivedAmount(20000L)
                .totalSentAmount(0L)
                .isMember(false)
                .build();
        Relation relation4 = Relation.builder()
                .member(members.get(2))
                .oppositeId(unknown2.getMemberId())
                .oppositeName(unknown2.getName())
                .relationType(RelationType.NONE)
                .description("WHO?")
                .totalReceivedAmount(50000L)
                .totalSentAmount(0L)
                .isMember(false)
                .build();
        relations.add(relation1);
        relations.add(relation2);
        relations.add(relation3);
        relations.add(relation4);
        members.get(1).getRelations().add(relation1);
        members.get(1).getRelations().add(relation2);
        members.get(2).getRelations().add(relation1);
        members.get(2).getRelations().add(relation2);
        members.add(unknown);
        members.add(unknown2);
        return relations;
    }
}
