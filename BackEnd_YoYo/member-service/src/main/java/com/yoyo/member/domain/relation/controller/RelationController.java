//package com.yoyo.member.domain.relation.controller;
//
//import com.yoyo.common.response.ApiResponse;
//import com.yoyo.member.domain.relation.dto.RelationDTO;
//import com.yoyo.member.domain.relation.service.RelationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/yoyo/members")
//@RequiredArgsConstructor
//public class RelationController {
//    private final RelationService relationService;
//
//    @GetMapping("/transactions")
//    public ResponseEntity<?> getRelationList(@RequestHeader("memberId") String memberId,
//                                             @RequestParam(required = false) String tag,
//                                             @RequestParam(required = false) String search,
//                                             @RequestParam(defaultValue = "true") boolean isRegister) {
//        ApiResponse<List<RelationDTO.Response>> response = new ApiResponse<>(
//                HttpStatus.OK.value(),
//                "리스트 조회 성공",
//                relationService.findRelations(Long.parseLong(memberId), tag, search, isRegister)
//        );
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//}
