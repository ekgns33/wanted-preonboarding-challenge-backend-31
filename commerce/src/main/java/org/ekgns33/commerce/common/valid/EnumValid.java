package org.ekgns33.commerce.common.valid;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumValid {
    String message() default "Enum에 없는 값입니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<? extends Enum<?>> enumClass();

    boolean ignoreCase() default false;
}