package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long studentId;
    private Long courseId;
    private String lastName;
    private String firstName;
    private String studentNumber;
    private String gender;
}
