package com.example.demo.dto;

import com.example.demo.validator.ValidGender;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
