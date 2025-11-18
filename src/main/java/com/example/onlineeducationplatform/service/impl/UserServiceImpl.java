package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.UserMapper;
import com.example.onlineeducationplatform.mapper.RoleMapper;
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
    @Autowired
    private RoleMapper roleMapper;

    // Note: Passwords are stored in plain text per user request. This is NOT
    // recommended for production.

    @Override
    public User getUserById(Integer id) {
        User u = userMapper.selectUserById(id);
        if (u != null) {
            u.setPassword(null); // hide password on fetch
            u.setRoles(roleMapper.selectRoleNamesByUserId(u.getId()));
        }
        return u;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = userMapper.selectAllUsers();
        if (list != null) {
            for (User u : list) {
                if (u != null) {
                    u.setPassword(null);
                    u.setRoles(roleMapper.selectRoleNamesByUserId(u.getId()));
                }
            }
        }
        return list;
    }

    @Override
    public int addUser(User user) {
        // Store password as-is (no hashing)
        int rows = userMapper.insertUser(user);
        // assign default USER role if exists
        if (rows > 0) {
            Integer roleId = roleMapper.selectRoleIdByName("USER");
            if (roleId != null && user.getId() != null) {
                roleMapper.insertUserRole(user.getId(), roleId);
            }
        }
        return rows;
    }

    @Override
    public int updateUser(User user) {
        // Store password as-is (no hashing)
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public User getUserByUsername(String username) {
        User u = userMapper.selectUserByUsername(username);
        if (u != null) {
            u.setRoles(roleMapper.selectRoleNamesByUserId(u.getId()));
        }
        return u;
    }

    @Override
    public boolean checkPassword(String rawPassword, String storedPassword) {
        // Plain text comparison (no hashing)
        if (rawPassword == null && storedPassword == null)
            return true;
        if (rawPassword == null || storedPassword == null)
            return false;
        return rawPassword.equals(storedPassword);
    }

    @Override
    public void registerUser(User user) {
        // Store password as-is (no hashing)
        userMapper.insertUser(user);
        Integer roleId = roleMapper.selectRoleIdByName("USER");
        if (roleId != null && user.getId() != null) {
            roleMapper.insertUserRole(user.getId(), roleId);
        }
    }
}