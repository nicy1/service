package com.example.demo.util;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCode {

    LASTNAME(100),
    FIRSTNAME(101),
    GENDER(102),
    COURSEID(103),
    STUDENTID(104),
    FACULTYID(105),
    NAME(106),
    PROFESSORNAME(107),
    STARTDATE(108),
    STUDENTNUMBER(109),
    SERVER_ERROR(110),
    RESOURCE_NOT_FOUND(111);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public static Integer getRelativeCode(String fieldName) {
        return Arrays.stream(ErrorCode.values())
                .filter(err -> err.name().equalsIgnoreCase(fieldName))
                .map(ErrorCode::getCode)
                .findFirst().orElse(null);
    }
}
