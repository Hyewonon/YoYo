package com.yoyo.member.application.port.in;

import com.yoyo.common.annotation.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RegisterMemberCommand extends SelfValidating<RegisterMemberCommand> {

    @NotNull
    private final String name;

    @NotNull
    private final String phoneNumber;
    @NotNull
    @NotBlank
    private final String password;
    @NotNull
    private final LocalDate birthDay;

    public RegisterMemberCommand(String name, String phoneNumber, String password, LocalDate birthDay, String pin, String fcmToken) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthDay = birthDay;
        this.validateSelf();
    }
}
