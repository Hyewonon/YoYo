package com.yoyo.notification.domain.notification.controller;

import com.yoyo.notification.domain.notification.dto.NotificationCreateDTO;
import com.yoyo.notification.domain.notification.dto.NotificationDTO;
import com.yoyo.notification.domain.notification.dto.NotificationDTO.Response;
import com.yoyo.notification.domain.notification.service.NotificationService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification API", description = "NotificationController")
@RestController
@RequestMapping("/yoyo/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 조회", description = "내 알림 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 목록 조회 성공",
                         content = @Content(schema = @Schema(implementation = NotificationDTO.Response.class)))
    })
    @GetMapping
    public ResponseEntity<?> getNotificationList(@RequestHeader("memberId") Long memberId,
                                                 @RequestParam("type") String type) {
        List<Response> responses = notificationService.getNotificationList(memberId, type);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @Operation(summary = "알림 생성 (테스트용)", description = "알림을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "알림 등록 성공",
                         content = @Content(schema = @Schema(implementation = NotificationCreateDTO.Response.class)))
    })
    @PostMapping
    public ResponseEntity<?> createNotification(@RequestHeader("memberId") Long memberId,
                                                @RequestBody NotificationCreateDTO.Request request) {
        NotificationCreateDTO.Response response = notificationService.createNotification(memberId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
