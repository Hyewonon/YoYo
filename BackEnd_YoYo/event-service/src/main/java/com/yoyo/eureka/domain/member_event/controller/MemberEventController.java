package com.yoyo.eureka.domain.member_event.controller;

import com.yoyo.eureka.domain.member_event.dto.MemberEventCreateDTO;
import com.yoyo.eureka.domain.member_event.dto.MemberEventDTO;
import com.yoyo.eureka.domain.member_event.dto.MemberEventTransactionDTO;
import com.yoyo.eureka.domain.member_event.service.MemberEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/schedule")
@RequiredArgsConstructor
public class MemberEventController {

    private final Long memberId = 1L;
    private final MemberEventService memberEventService;

    @Operation(summary = "일정 목록 조회", description = "일정 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 목록 조회 성공",
                         content = @Content(schema = @Schema(implementation = MemberEventDTO.Response.class)))
    })
    @GetMapping
    public ResponseEntity<?> getScheduleList(Long memberId) {
        List<MemberEventDTO.Response> responses = memberEventService.getScheduleList(this.memberId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @Operation(summary = "일정 상세 조회", description = "일정 상세를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 상세 조회 성공",
                         content = @Content(schema = @Schema(implementation = MemberEventTransactionDTO.Response.class))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 일정입니다.")
    })
    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getSchedule(Long memberId, @PathVariable("scheduleId") Long memberEventId,
                                         @RequestParam("type") String type) {
        MemberEventTransactionDTO.Response response = memberEventService.getSchedule(this.memberId, memberEventId,
                                                                                     type);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "일정 등록", description = "새로운 일정을 등록한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "일정 등록 성공",
                         content = @Content(schema = @Schema(implementation = MemberEventCreateDTO.class))),
            @ApiResponse(responseCode = "409", description = "이미 등록된 일정입니다.")
    })
    @PostMapping
    public ResponseEntity<?> createSchedule(Long memberId, @RequestBody MemberEventCreateDTO request) {
        MemberEventCreateDTO response = memberEventService.createSchedule(this.memberId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
