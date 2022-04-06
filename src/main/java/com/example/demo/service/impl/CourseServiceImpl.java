package com.example.demo.service.impl;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.CreateCourseDto;
import com.example.demo.dto.UpdateCourseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UniqueConstraintViolationException;
import com.example.demo.model.Course;
import com.example.demo.model.Faculty;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.service.CourseService;
import com.example.demo.util.ErrorCode;
import com.example.demo.util.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final FacultyRepository facultyRepo;
    private final Mappers mapper;


    @Override
    public Long createCourse(CreateCourseDto request) {

        if (this.existsByName(request.getName())) {
            throw new UniqueConstraintViolationException(Course.class.getName(), ErrorCode.NAME.name(), request.getName());
        }

        // It'll always exist in db
        Faculty faculty = facultyRepo.findById(request.getFacultyId()).get();

        Course course = mapper.map(request);
        course.setFaculty(faculty);
        return courseRepo.save(course).getId();
    }

    @Override
    public Optional<Course> getCourse(Long id) {
        return courseRepo.findById(id);
    }

    @Override
    public Page<Course> getCourses(String facultyName, String name, String professorName, Date startDate, Pageable page) {

        return courseRepo.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (facultyName != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("faculty").get("name"), "%" + facultyName + "%")));
            }
            if (name != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%" + name + "%")));
            }
            if (professorName != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("professorName"), "%" + professorName + "%")));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("startDate"), "%" + startDate + "%")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, page);
    }

    @Override
    @Transactional
    public void updateCourse(Long courseId, UpdateCourseDto newData) {
        this.getCourse(courseId)
                .map(course -> this.update(course, newData))
                .orElseThrow(() -> new ResourceNotFoundException(Course.class.getName(), courseId.toString()));
    }

    private Course update(Course course, UpdateCourseDto newCourse) {
        if (newCourse.getName() != null && !course.getName().equalsIgnoreCase(newCourse.getName())) {

            if (this.existsByName(newCourse.getName())) {
                throw new UniqueConstraintViolationException(Course.class.getName(), ErrorCode.NAME.name(), newCourse.getName());
            }
            course.setName(newCourse.getName());
        }
        if (newCourse.getProfessorName() != null) {
            course.setProfessor(newCourse.getProfessorName());
        }
        if (newCourse.getStartDate() != null) {
            course.setStartDate(newCourse.getStartDate());
        }

        facultyRepo.findById(newCourse.getFacultyId()).ifPresent(course::setFaculty);
        return course;
    }

    private boolean existsByName(String name) {
       return courseRepo.existsByName(name);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }
}
