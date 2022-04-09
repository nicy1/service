package com.example.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {

    String message() default "Gender must be 'M', 'F', or 'OTHER'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}