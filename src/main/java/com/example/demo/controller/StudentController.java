package com.example.demo.controller;

import com.example.demo.dto.CreateStudentDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.exception.UniqueConstraintViolationException;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/courses/{courseId}/students")
public class StudentController {

    private final StudentService studentService;
    private final Mappers mapper;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a student",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", headers = @Header(name = HttpHeaders.LOCATION)),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<String> createStudent(@RequestBody @Valid CreateStudentDto request) {

        if (studentService.existsByStudentNumber(request.getStudentNumber())) {
            throw new UniqueConstraintViolationException(Student.class.getName(), request.getStudentNumber());
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{studentId}")
                .buildAndExpand(studentService.createStudent(request)).toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping(path = "/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get a student",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = StudentDto.class))),
                    @ApiResponse(description = "Student not found", responseCode = "404", content = {}),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<StudentDto> getStudent(@PathVariable("studentId") Long studentId) {
        return studentService.getStudent(studentId)
                .map(student -> ResponseEntity.ok().body(mapper.map(student)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Fetch students parametrized",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<Page<StudentDto>> getStudents(@RequestParam(required = false) String lastName,
                                                        @RequestParam(required = false) String firstName,
                                                        @RequestParam(required = false) String gender,
                                                        @RequestParam(required = false, defaultValue = "0") Integer offset,
                                                        @RequestParam(required = false, defaultValue = "10") Integer limit) {

        Page<Student> studentsPage = studentService.getStudents(lastName, firstName, gender, PageRequest.of(offset, limit));
        return ResponseEntity.ok().body(studentsPage.map(mapper::map));
    }


    @PutMapping(path = "/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update a student",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = {}),
                    @ApiResponse(description = "Student not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<Void> updateStudent(@PathVariable("studentId") Long studentId, @RequestBody StudentDto newData) {
        studentService.updateStudent(studentId, newData);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path = "/{studentId}")
    @Operation(
            summary = "Delete a student",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = {}),
                    @ApiResponse(description = "Student not found", responseCode = "404", content = {}),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

}
