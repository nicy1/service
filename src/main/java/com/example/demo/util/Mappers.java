package com.example.demo.util;

import com.example.demo.dto.*;
import com.example.demo.model.Course;
import com.example.demo.model.Faculty;
import com.example.demo.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Mappers {

    @Mapping(target = "professor", source = "professorName")
    Course map(CreateCourseDto request);

    Student map(CreateStudentDto request);

    @Mapping(target = "professorName", source = "professor")
    @Mapping(target = "facultyId", source = "faculty.id")
    CourseDto map(Course course);

    @Mapping(target = "studentId", source = "id")
    @Mapping(target = "courseId", source = "course.id")
    StudentDto map(Student student);

    FacultyDto map(Faculty faculty);
}
