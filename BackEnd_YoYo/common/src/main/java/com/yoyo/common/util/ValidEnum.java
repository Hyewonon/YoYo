package com.yoyo.common.util;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    String message() default "올바른 카테고리가 아닙니다.";
    Class[] groups() default {};
    Class[] payload() default {};

    Class<? extends java.lang.Enum<?>> enumClass();
}
