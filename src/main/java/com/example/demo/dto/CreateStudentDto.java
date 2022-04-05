package com.example.demo.dto;

import com.example.demo.validator.ValidGender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CreateStudentDto {

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String studentNumber;

    @NotEmpty
    @ValidGender
    private String gender;
}
