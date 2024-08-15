package com.lingg.hellospringboot.controller;

import com.lingg.hellospringboot.dto.request.ApiResponse;
import com.lingg.hellospringboot.dto.request.UserCreationRequest;
import com.lingg.hellospringboot.dto.request.UserUpdateRequest;
import com.lingg.hellospringboot.entity.User;
import com.lingg.hellospringboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping()
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(String id, @RequestBody UserUpdateRequest request){
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "true";
    }
}
