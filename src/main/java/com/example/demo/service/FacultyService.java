package com.example.demo.service;

import com.example.demo.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FacultyService {

    Page<Faculty> getFaculties(String name, Pageable page);
}
