package com.yoyo.member.application.port.in.member;

import com.yoyo.common.annotation.SelfValidating;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterMemberCommand extends SelfValidating<RegisterMemberCommand> {

    @NotNull
    private final String name;

    @NotNull
    private final String phoneNumber;

    @NotBlank
    private final String password;

    @NotNull
    private final LocalDate birthDay;

    private final boolean isValid;

    public RegisterMemberCommand(String name, String phoneNumber, String password, LocalDate birthDay, boolean isValid) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthDay = birthDay;
        this.isValid = isValid;
        this.validateSelf(); // 생성자에서 유효성 검증
    }
}
