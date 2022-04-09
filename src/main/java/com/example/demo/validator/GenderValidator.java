package com.example.demo.validator;

import com.example.demo.util.Gender;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@NoArgsConstructor
public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        return Arrays.stream(Gender.values())
                .map(Gender::name)
                .anyMatch(g -> g.equals(gender));
    }
}
