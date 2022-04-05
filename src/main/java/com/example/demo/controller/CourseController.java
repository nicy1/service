package com.example.demo.controller;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.CreateCourseDto;
import com.example.demo.exception.UniqueConstraintViolationException;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import com.example.demo.util.Mappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/courses")
public class CourseController {

    private final CourseService courseService;
    private final Mappers mapper;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a course",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", headers = @Header(name = HttpHeaders.LOCATION)),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<String> createCourse(@RequestBody @Valid CreateCourseDto request) {

        if (courseService.existsByName(request.getName())) {
            throw new UniqueConstraintViolationException(Course.class.getName(), request.getName());
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{courseId}")
                .buildAndExpand(courseService.createCourse(request)).toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping(path = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get a course",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = CourseDto.class))),
                    @ApiResponse(description = "Course not found", responseCode = "404", content = {}),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<CourseDto> getCourse(@PathVariable("courseId") Long courseId) {
        return courseService.getCourse(courseId)
                .map(course -> ResponseEntity.ok().body(mapper.map(course)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Fetch courses parametrized",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<Page<CourseDto>> getCourses(@RequestParam(required = false) String facultyName,
                                                      @RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String professorName,
                                                      @RequestParam(required = false) Date startDate,
                                                      @RequestParam(required = false, defaultValue = "0") Integer offset,
                                                      @RequestParam(required = false, defaultValue = "10") Integer limit) {

        Page<Course> coursesPage = courseService.getCourses(facultyName, name, professorName, startDate, PageRequest.of(offset, limit));
        return ResponseEntity.ok().body(coursesPage.map(mapper::map));
    }


    @PutMapping(path = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update a course",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = {}),
                    @ApiResponse(description = "Course not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<Void> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseDto newData) {
        courseService.updateCourse(courseId, newData);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path = "/{courseId}")
    @Operation(
            summary = "Delete a course",
            responses = {@ApiResponse(description = "Ok", responseCode = "200", content = {}),
                    @ApiResponse(description = "Course not found", responseCode = "404", content = {}),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }

}
