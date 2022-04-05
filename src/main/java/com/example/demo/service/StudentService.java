package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;
import com.example.demo.dto.CreateStudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {

    Long createStudent(CreateStudentDto request);
    Optional<Student> getStudent(Long id);
    Page<Student> getStudents(String lastName, String firstName, String gender, Pageable page);
    void updateStudent(Long studentId, StudentDto newData);

    boolean existsByStudentNumber(String studentNumber);

    void deleteStudent(Long id);
}
