package com.lingg.hellospringboot.services;

import com.lingg.hellospringboot.dto.request.UserCreationRequest;
import com.lingg.hellospringboot.dto.request.UserUpdateRequest;
import com.lingg.hellospringboot.entity.User;
import com.lingg.hellospringboot.exception.AppException;
import com.lingg.hellospringboot.exception.ErrorCode;
import com.lingg.hellospringboot.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;

    public User createUser(UserCreationRequest request){
        User user = new User();

        if(repository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());
        return repository.save(user);
    }

    public List<User> getUsers(){
        return repository.findAll();
    }

    public User getUserById(String id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String id, UserUpdateRequest request){
        User user = getUserById(id);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());
        return repository.save(user);
    }

    public void deleteUser(String id){
        repository.deleteById(id);
    }
}
