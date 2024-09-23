package com.yoyo.member.adapter.in.web.relation;

import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import com.yoyo.member.application.service.RelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindRelationController {
    private final RelationService relationService;

    @GetMapping("/yoyo/members/transactions")
    public ResponseEntity<?> getRelationList(@RequestHeader("memberId") String memberId,
                                             @RequestParam(required = false) String tag,
                                             @RequestParam(required = false) String search) {
        return ResponseEntity.ok(relationService.findRelations(Long.parseLong(memberId), tag, search));
    }
}
