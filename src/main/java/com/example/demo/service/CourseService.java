package com.example.demo.service;

import com.example.demo.dto.CourseDto;
import com.example.demo.model.Course;
import com.example.demo.dto.CreateCourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface CourseService {

    Long createCourse(CreateCourseDto request);

    Optional<Course> getCourse(Long id);

    Page<Course> getCourses(String facultyName, String name, String professorName, Date startDate, Pageable page);

    void updateCourse(Long courseId, CourseDto newData);

    boolean existsByName(String name);

    void deleteCourse(Long id);
}
