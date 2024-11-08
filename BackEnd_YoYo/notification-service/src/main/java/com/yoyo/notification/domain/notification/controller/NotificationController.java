package com.yoyo.notification.domain.notification.controller;

import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.exception.ErrorResponse;
import com.yoyo.notification.domain.notification.dto.NotificationDTO;
import com.yoyo.notification.domain.notification.dto.NotificationDTO.Response;
import com.yoyo.notification.domain.notification.dto.NotificationUpdateDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(summary = "이벤트 알림 응답", description = "이벤트 알림의 응답을 반영합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 응답 성공",
                         content = @Content(schema = @Schema(implementation = NotificationUpdateDTO.class)))
    })
    @PatchMapping
    public ResponseEntity<?> updateNotification(@RequestHeader("memberId") Long memberId,
                                                @RequestBody NotificationUpdateDTO request) {
        NotificationUpdateDTO response = notificationService.updateNotification(memberId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "이벤트 알림 삭제", description = "이벤트 알림을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 삭제 성공",
                         content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효한 알림 id 아님",
                         content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "삭제 요청자와 알림 수신자가 다름",
                         content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@RequestHeader("memberId") Long memberId,
                                                @PathVariable("notificationId") Long notificationId) {
        CommonResponse response = notificationService.deleteNotification(memberId, notificationId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
