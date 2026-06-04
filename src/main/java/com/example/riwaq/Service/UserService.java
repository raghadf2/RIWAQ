package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.In.UserDtoIn;
import com.example.riwaq.Model.User;
import com.example.riwaq.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public void addUser(UserDtoIn dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());

        userRepository.save(user);

        notificationService.sendWelcomeNotification(user.getId());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Integer userId, UserDtoIn dto) {

        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User not found");
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());

        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {

        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User not found");
        }

        userRepository.delete(user);
    }

    // endpoint
    public User getUserByUsername(String username){

        User user = userRepository.findUserByUsername(username);

        if(user == null){
            throw new ApiException("User not found");
        }

        return user;
    }
}
