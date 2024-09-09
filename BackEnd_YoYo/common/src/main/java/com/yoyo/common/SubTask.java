package com.yoyo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTask {

    private Long memberId;
    private String subTaskName;
    private String taskType;
    private String status;
}
