package com.yoyo.member.domain.relation.controller;

import com.yoyo.common.response.ApiResponse;
import com.yoyo.member.domain.relation.dto.UpdateRelationDTO;
import com.yoyo.member.domain.relation.service.RelationService;
import com.yoyo.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/members/relation")
@RequiredArgsConstructor
public class UpdateRelationController {

    private final RelationService relationService;

//   private Long memberId = 999999999L;

    /**
     * 친구관계 정보 수정
     *
     * @param request 태그, 메모
     */
    @PatchMapping
    ResponseEntity<?> updateRelation(@RequestHeader("memberId") Long memberId,
                                     @RequestBody UpdateRelationDTO.Request request) {
        relationService.updateRelation(memberId, request);
        ApiResponse<Member> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "친구관계 수정",
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
