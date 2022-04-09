package com.example.demo.util;

import java.util.Arrays;

public enum Gender {

    M,
    F,
    OTHER;

    public static boolean contains(String gender) {
        return Arrays.stream(Gender.values())
                .map(Gender::name)
                .anyMatch(g -> g.equals(gender));
    }
}
