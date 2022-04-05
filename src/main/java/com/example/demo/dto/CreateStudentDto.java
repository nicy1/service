package com.example.demo.dto;

import com.example.demo.validator.ValidGender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CreateStudentDto {

    @NotNull(message = "{constraints.NotNull.message}")
    private Long courseId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String lastName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String firstName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String studentNumber;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @ValidGender
    private String gender;
}
