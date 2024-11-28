package com.lingg.hellospringboot.services;

import com.lingg.hellospringboot.dto.request.UserCreationRequest;
import com.lingg.hellospringboot.dto.response.UserResponse;
import com.lingg.hellospringboot.entity.User;
import com.lingg.hellospringboot.exception.AppException;
import com.lingg.hellospringboot.repositories.IUserRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private IUserRepository repository;
    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2001, 9, 4);
        request = UserCreationRequest.builder()
                .username("nql")
                .firstName("nq")
                .lastName("l")
                .password("12345678'")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("abc12345")
                .username("nql")
                .firstName("nq")
                .lastName("l")
                .dob(dob)
                .build();

        user = User.builder()
                .id("abc12345")
                .username("nql")
                .firstName("nq")
                .lastName("l")
                .dob(dob)
                .build();
    }

    @Test
    void create_validRequest_success() {
        // GIVEN
        when(repository.existsByUsername(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("abc12345");
        Assertions.assertThat(response.getUsername()).isEqualTo("nql");
    }

    @Test
    void create_userExisted_fail() {
        // GIVEN
        when(repository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "nql1")
    void getMyInfo_valid_success() {
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();
        Assertions.assertThat(response.getId()).isEqualTo("abc12345");
        Assertions.assertThat(response.getUsername()).isEqualTo("nql");
    }

    @Test
    @WithMockUser(username = "nql1")
    void getMyInfo_userNotFound_error() {
        when(repository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}
