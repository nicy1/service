package com.example.demo.service.impl;

import com.example.demo.dto.CreateCourseDto;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.util.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepoMock;
    @Mock
    private FacultyRepository facultyRepoMock;
    @Mock
    private Mappers mapperMock;

    @InjectMocks
    private CourseServiceImpl courseServiceMock;

    CreateCourseDto request;

    @BeforeEach
    void setUp() {
        CreateCourseDto.builder()
                .facultyId(1L).name("Network")
                .professorName("prof")
                .startDate(new Date())
                .build();

    }

    @Test
    void createCourse() {
   /*     given(courseRepoMock.existsByName("Network")).willReturn(false);
        given(facultyRepoMock.findById(1L)).willReturn(document);

       given(passwordEncoderMock.encode(anyString())).willReturn(credentials.getPassword());

        lenient().doNothing().when(fileServiceMock).send(any(), any(), any(), any(), any(), eq(false));
        lenient().doNothing().when(notificationServiceMock).send(any(Notification.class));

        given(requestServiceMock.save(any(Request.class))).willReturn(any());
        ;

        assertNotNull(
                userService.register(profile, enterprise, credentials, document, dFront, dBack, picture)
        );


        verify(userRepoMock).existsByEmail(anyString());
        verify(userRepoMock).save(any(User.class));
        verify(roleRepoMock).findByName(anyString());*/
    }

    @Test
    void getCourse() {
    }

    @Test
    void getCourses() {
    }

    @Test
    void updateCourse() {
    }

    @Test
    void deleteCourse() {
    }
}