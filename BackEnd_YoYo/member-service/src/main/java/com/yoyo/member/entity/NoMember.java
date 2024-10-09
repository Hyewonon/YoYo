package com.yoyo.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "no_member")
@Getter
@SuperBuilder
@AllArgsConstructor
public class NoMember extends BaseMember{

}