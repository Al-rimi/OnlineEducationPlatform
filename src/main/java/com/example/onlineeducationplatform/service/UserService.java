package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.User;
import java.util.List;

public interface UserService {
    User getUserById(Integer id);

    List<User> getAllUsers();

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);

    // For authentication and registration
    User getUserByUsername(String username);

    boolean checkPassword(String rawPassword, String encodedPassword);

    void registerUser(User user);
}