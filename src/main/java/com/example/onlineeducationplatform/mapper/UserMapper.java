package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    User selectUserById(Integer id);

    List<User> selectAllUsers();

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);

    // For authentication
    User selectUserByUsername(String username);
}