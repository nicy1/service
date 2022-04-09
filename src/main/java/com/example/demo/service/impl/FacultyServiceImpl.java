package com.example.demo.service.impl;

import com.example.demo.model.Faculty;
import com.example.demo.model.Student;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepo;


    @Override
    public Page<Faculty> getFaculties(String name, Pageable page) {

        return facultyRepo.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%" + name + "%")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, page);
    }
}
