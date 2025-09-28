package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.UserMapper;
import com.example.onlineeducationplatform.model.User;
import com.example.onlineeducationplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public User getUserByUsername(String username) {
        // Implement a method in UserMapper for this, or use selectAllUsers and filter
        // (not efficient)
        List<User> users = userMapper.selectAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        // Use BCryptPasswordEncoder for encoded passwords
        org.springframework.security.crypto.password.PasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void registerUser(User user) {
        // Encode password before saving
        org.springframework.security.crypto.password.PasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }
}