package com.lingg.hellospringboot.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lingg.hellospringboot.dto.request.UserCreationRequest;
import com.lingg.hellospringboot.dto.request.UserUpdateRequest;
import com.lingg.hellospringboot.dto.response.UserResponse;
import com.lingg.hellospringboot.entity.User;
import com.lingg.hellospringboot.enums.Role;
import com.lingg.hellospringboot.exception.AppException;
import com.lingg.hellospringboot.exception.ErrorCode;
import com.lingg.hellospringboot.mapper.UserMapper;
import com.lingg.hellospringboot.repositories.IUserRepository;
import com.lingg.hellospringboot.repositories.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    //    @Autowired
    IUserRepository repository;
    RoleRepository roleRepository;
    //    @Autowired
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        // kiem tar user ton tai?
        if (repository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // map request vap user
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add((Role.USER.name()));
        // user.setRoles(roles);
        // luu db
        return userMapper.toUserResponse(repository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("getUsers");
        return repository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username = authentication.name")
    public UserResponse getUserById(String id) {
        log.info("getUser by Id");
        return userMapper.toUserResponse(
                repository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        //        User user = getUserById(id);
        //
        //        user.setPassword(request.getPassword());
        //        user.setFirstName(request.getFirstName());
        //        user.setLastName(request.getLastName());
        //        user.setDob(request.getDob());
        //        return repository.save(user);

        // su dung mapper
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(repository.save(user));
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = repository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
