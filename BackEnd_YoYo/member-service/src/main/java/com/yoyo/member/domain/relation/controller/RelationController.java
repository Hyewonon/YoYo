package com.yoyo.member.domain.relation.controller;

import com.yoyo.common.response.ApiResponse;
import com.yoyo.member.domain.relation.dto.RelationDTO;
import com.yoyo.member.domain.relation.dto.UpdateRelationDTO;
import com.yoyo.member.domain.relation.service.RelationService;
import com.yoyo.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/yoyo")
@RequiredArgsConstructor
public class RelationController {
    private final RelationService relationService;
    /**
     * @Header memberId 현재 사용자
     * @param tag 태그
     * @param search 이름 검색
     * @param isRegister 등록 대기
     * @return 리스트 조회
     */
    @GetMapping("/members-relations")
    public ResponseEntity<?> getRelationList(@RequestHeader("memberId") String memberId,
                                             @RequestParam(required = false) String tag,
                                             @RequestParam(required = false) String search) {
        ApiResponse<List<RelationDTO.Response>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "리스트 조회 성공",
                relationService.findRelations(Long.parseLong(memberId), tag, search)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 친구관계 정보 수정
     *
     * @param request 태그, 메모
     */
    @PatchMapping("/members-relation")
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
