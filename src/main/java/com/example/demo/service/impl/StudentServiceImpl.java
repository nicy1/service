package com.example.demo.service.impl;

import com.example.demo.dto.CreateStudentDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.UpdateStudentDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UniqueConstraintViolationException;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import com.example.demo.util.ErrorCode;
import com.example.demo.util.Gender;
import com.example.demo.util.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final Mappers mapper;


    @Override
    public Long createStudent(Long courseId, CreateStudentDto request) {

        if (this.existsByStudentNumber(request.getStudentNumber())) {
            throw new UniqueConstraintViolationException(Student.class.getName(), ErrorCode.STUDENTNUMBER.name(), request.getStudentNumber());
        }

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class.getName(), courseId.toString()));

        Student student = mapper.map(request);
        student.setCourse(course);
        return studentRepo.save(student).getId();
    }

    @Override
    public Optional<Student> getStudent(Long id) {
        return studentRepo.findById(id);
    }

    @Override
    public Page<Student> getStudents(String lastName, String firstName, String gender, Pageable page) {

        return studentRepo.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (lastName != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%")));
            }
            if (firstName != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%")));
            }
            if (gender != null && Gender.contains(gender)) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("gender"), "%" + gender + "%")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, page);
    }

    @Override
    @Transactional
    public void updateStudent(Long studentId, UpdateStudentDto newData) {
        this.getStudent(studentId)
                .map(student -> this.update(student, newData))
                .orElseThrow(() -> new ResourceNotFoundException(Student.class.getName(), studentId.toString()));
    }

    private Student update(Student student, UpdateStudentDto newStudent) {
        if (!student.getStudentNumber().equalsIgnoreCase(newStudent.getStudentNumber())) {

            if (this.existsByStudentNumber(newStudent.getStudentNumber())) {
                throw new UniqueConstraintViolationException(Course.class.getName(), ErrorCode.STUDENTNUMBER.name(), newStudent.getStudentNumber());
            }
            student.setStudentNumber(newStudent.getStudentNumber());
        }
        if (!student.getLastName().equalsIgnoreCase(newStudent.getLastName())) {
            student.setLastName(newStudent.getLastName());
        }
        if (!student.getFirstName().equalsIgnoreCase(newStudent.getFirstName())) {
            student.setFirstName(newStudent.getFirstName());
        }
        if (!student.getGender().equalsIgnoreCase(newStudent.getGender())) {
            student.setGender(newStudent.getGender());
        }
        if (!student.getCourse().getId().equals(newStudent.getCourseId())) {
            courseRepo.findById(newStudent.getCourseId()).ifPresent(student::setCourse);
        }

        return student;
    }

    private boolean existsByStudentNumber(String studentNumber) {
        return studentRepo.existsByStudentNumber(studentNumber);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);
    }
}
