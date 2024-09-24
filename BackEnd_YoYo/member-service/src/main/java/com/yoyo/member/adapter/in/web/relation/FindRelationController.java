package com.yoyo.member.adapter.in.web.relation;

import com.yoyo.member.adapter.out.RelationResponse;
import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import com.yoyo.member.application.service.RelationService;
import com.yoyo.member.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FindRelationController {
    private final RelationService relationService;

    @GetMapping("/yoyo/members/transactions")
    public ResponseEntity<?> getRelationList(@RequestHeader("memberId") String memberId,
                                             @RequestParam(required = false) String tag,
                                             @RequestParam(required = false) String search,
                                             @RequestParam(defaultValue = "true") boolean isRegister) {
        ApiResponse<List<RelationResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "리스트 조회 성공",
                relationService.findRelations(Long.parseLong(memberId), tag, search, isRegister)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
