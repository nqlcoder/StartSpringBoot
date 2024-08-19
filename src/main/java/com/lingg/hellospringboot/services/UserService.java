package com.lingg.hellospringboot.services;

import com.lingg.hellospringboot.dto.request.UserCreationRequest;
import com.lingg.hellospringboot.dto.request.UserUpdateRequest;
import com.lingg.hellospringboot.dto.response.UserResponse;
import com.lingg.hellospringboot.entity.User;
import com.lingg.hellospringboot.exception.AppException;
import com.lingg.hellospringboot.exception.ErrorCode;
import com.lingg.hellospringboot.mapper.UserMapper;
import com.lingg.hellospringboot.repositories.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    //    @Autowired
    IUserRepository repository;
    //    @Autowired
    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
        //kiem tar user ton tai?
        if (repository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        //map request vap user
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //luu db
        return repository.save(user);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
//        User user = getUserById(id);
//
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
//        return repository.save(user);

        //su dung mapper
        User user = repository.findById(id).
                orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(repository.save(user));
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}
