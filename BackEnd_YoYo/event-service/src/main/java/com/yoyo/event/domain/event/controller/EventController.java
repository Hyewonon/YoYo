package com.yoyo.event.domain.event.controller;

import com.yoyo.event.domain.event.dto.EventDTO;
import com.yoyo.event.domain.event.dto.EventDetailDTO;
import com.yoyo.event.domain.event.dto.EventUpdateDTO;
import com.yoyo.event.domain.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Event API", description = "EventController")
@RestController
@RequestMapping("/yoyo/event")
@RequiredArgsConstructor
public class EventController {

    private final Long memberId = 1L;

    private final EventService eventService;

    @Operation(summary = "이벤트 생성", description = "이벤트를 생성한다.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "이벤트 생성 성공",
                         content = {@Content(schema = @Schema(implementation = EventDTO.Response.class))})
    })
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO.Request request, Long memberId){
        EventDTO.Response response = eventService.createEvent(request, memberId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록을 조회한다.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "이벤트 목록 조회 성공",
                         content = @Content(schema = @Schema(implementation = EventDTO.Response.class)))
    })
    @GetMapping
    public ResponseEntity<?> getEventList(Long memberId){
        List<EventDTO.Response> responses = eventService.getEventList(memberId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @Operation(summary = "이벤트 상세 조회", description = "이벤트 상세를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 상세 조회 성공",
                         content = @Content(schema = @Schema(implementation = EventDetailDTO.Response.class))),
            @ApiResponse(responseCode = "400", description = "이벤트가 존재하지 않습니다.")
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable("eventId") Long eventId, @RequestParam String tag, @RequestParam boolean isRegister, Long memberId){
        EventDetailDTO.Response response = eventService.getEvent(memberId, eventId, tag, isRegister);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "이벤트 수정", description = "이벤트를 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 수정 성공",
                         content = @Content(schema = @Schema(implementation = EventDTO.Response.class))),
            @ApiResponse(responseCode = "400", description = "이벤트가 존재하지 않습니다.")
    })
    @PatchMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable("eventId") Long eventId, @RequestBody EventUpdateDTO.Request request, Long memberId){
        EventDTO.Response response = eventService.updateEvent(eventId, memberId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
