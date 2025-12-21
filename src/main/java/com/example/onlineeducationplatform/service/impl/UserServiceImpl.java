package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.UserMapper;
import com.example.onlineeducationplatform.mapper.RoleMapper;
import com.example.onlineeducationplatform.mapper.EnrollmentMapper;
import com.example.onlineeducationplatform.model.User;
import com.example.onlineeducationplatform.model.Role;
import com.example.onlineeducationplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private EnrollmentMapper enrollmentMapper;

    // Note: Passwords are stored in plain text per user request. This is NOT
    // recommended for production.

    @Override
    public User getUserById(Integer id) {
        User u = userMapper.selectUserById(id);
        if (u != null) {
            u.setPassword(null); // hide password on fetch
            // Fetch and set roles
            List<String> roleNames = getRoleNamesByUserId(id);
            List<Role> roles = new ArrayList<>();
            for (String roleName : roleNames) {
                Role role = new Role();
                role.setName(roleName);
                roles.add(role);
            }
            u.setRoles(roles);
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
                    // Fetch and set roles
                    List<String> roleNames = getRoleNamesByUserId(u.getId());
                    List<Role> roles = new ArrayList<>();
                    for (String roleName : roleNames) {
                        Role role = new Role();
                        role.setName(roleName);
                        roles.add(role);
                    }
                    u.setRoles(roles);
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
        int rows = userMapper.updateUser(user);
        if (rows > 0 && user.getRoles() != null) {
            // Update roles
            userMapper.deleteUserRoles(user.getId());
            for (Role role : user.getRoles()) {
                userMapper.insertUserRole(user.getId(), role.getName());
            }
        }
        return rows;
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public User getUserByUsername(String username) {
        User u = userMapper.selectUserByUsername(username);
        if (u != null) {
            // Do not call setRoles with List<String>
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

    @Override
    public java.util.List<String> getRoleNamesByUserId(Integer userId) {
        return roleMapper.selectRoleNamesByUserId(userId);
    }

    @Override
    public List<User> getUsersEnrolledInCourse(Integer courseId) {
        List<com.example.onlineeducationplatform.model.Enrollment> enrollments = enrollmentMapper
                .selectByCourse(courseId);
        List<User> users = new ArrayList<>();
        for (com.example.onlineeducationplatform.model.Enrollment enrollment : enrollments) {
            User user = userMapper.selectUserById(enrollment.getUserId());
            if (user != null) {
                user.setPassword(null); // hide password
                // Fetch and set roles
                List<String> roleNames = getRoleNamesByUserId(user.getId());
                List<Role> roles = new ArrayList<>();
                for (String roleName : roleNames) {
                    Role role = new Role();
                    role.setName(roleName);
                    roles.add(role);
                }
                user.setRoles(roles);
                users.add(user);
            }
        }
        return users;
    }
}