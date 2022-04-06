package com.example.demo.service;

import com.example.demo.dto.UpdateStudentDto;
import com.example.demo.model.Student;
import com.example.demo.dto.CreateStudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {

    Long createStudent(Long courseId, CreateStudentDto request);
    Optional<Student> getStudent(Long id);
    Page<Student> getStudents(Long courseId, String lastName, String firstName, String gender, Pageable page);
    void updateStudent(Long studentId, UpdateStudentDto newData);
    void deleteStudent(Long id);
}
