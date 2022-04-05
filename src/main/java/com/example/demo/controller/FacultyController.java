package com.example.demo.controller;

import com.example.demo.dto.FacultyDto;
import com.example.demo.model.Faculty;
import com.example.demo.service.FacultyService;
import com.example.demo.util.Mappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final Mappers mapper;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Fetch faculties parametrized",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)}
    )
    public ResponseEntity<Page<FacultyDto>> getFaculties(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false, defaultValue = "0") Integer offset,
                                                         @RequestParam(required = false, defaultValue = "10") Integer limit) {

        Page<Faculty> facultiesPage = facultyService.getFaculties(name, PageRequest.of(offset, limit));
        return ResponseEntity.ok().body(facultiesPage.map(mapper::map));
    }

}
