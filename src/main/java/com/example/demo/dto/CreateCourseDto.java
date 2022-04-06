package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDto {

    @NotNull
    private Long facultyId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String professorName;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy", lenient = OptBoolean.FALSE)
    private Date startDate;
}
