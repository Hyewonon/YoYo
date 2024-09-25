package com.yoyo.event.domain.event.controller;

import com.yoyo.event.domain.event.dto.EventDTO.Response;
import com.yoyo.event.domain.event.dto.EventDetailDTO;
import com.yoyo.event.domain.event.dto.EventUpdateDTO;
import com.yoyo.event.domain.event.service.EventService;
import com.yoyo.event.domain.event.dto.EventDTO.Request;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Event API", description = "EventController")
@RestController
@RequestMapping("/yoyo/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "이벤트 생성", description = "이벤트를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이벤트 생성 성공",
                    content = {@Content(schema = @Schema(implementation = Response.class))})
    })
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestHeader("memberId") String memberId, @RequestBody Request request) {
        Response response = eventService.createEvent(Long.parseLong(memberId), request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping
    public ResponseEntity<?> getEventList(@RequestHeader("memberId") String memberId) {
        List<Response> responses = eventService.getEventList(Long.parseLong(memberId));
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @Operation(summary = "이벤트 상세 조회", description = "이벤트 상세를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = EventDetailDTO.Response.class))),
            @ApiResponse(responseCode = "400", description = "이벤트가 존재하지 않습니다."),
            @ApiResponse(responseCode = "403", description = "이벤트 접근 권한이 없습니다.")
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@RequestHeader("memberId") String memberId, @PathVariable("eventId") Long eventId) {
        EventDetailDTO.Response response = eventService.getEvent(Long.parseLong(memberId), eventId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "이벤트 수정", description = "이벤트를 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 수정 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "이벤트가 존재하지 않습니다."),
            @ApiResponse(responseCode = "403", description = "이벤트 접근 권한이 없습니다.")
    })
    @PatchMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(@RequestHeader("memberId") String memberId,
                                         @PathVariable("eventId") Long eventId,
                                         @RequestBody EventUpdateDTO.Request request) {
        Response response = eventService.updateEvent(Long.parseLong(memberId), eventId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "이벤트 검색", description = "이벤트를 검색한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 검색 성공",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchEvent(@RequestHeader("memberId") String memberId,
                                         @RequestParam("keyword") String keyword,
                                         @RequestParam(defaultValue = "0", value = "pageNumber") int pageNumber) {
        Slice<Response> responses = eventService.searchEvent(Long.parseLong(memberId), keyword, pageNumber);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
