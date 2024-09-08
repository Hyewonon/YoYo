package com.yoyo.member.application.port.in;

import com.yoyo.common.annotation.SelfValidating;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateMemberCommand extends SelfValidating<UpdateMemberCommand> {

    private final Long memberId;
    private final String name;

    private final String phoneNumber;

    private final LocalDate birthDay;


    public UpdateMemberCommand(Long memberId, String name, String phoneNumber, LocalDate birthDay
                               ) {
        this.memberId = memberId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;

        this.validateSelf();
    }
}
