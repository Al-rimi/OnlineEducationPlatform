package com.example.onlineeducationplatform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RoleMapper {
    List<String> selectRoleNamesByUserId(@Param("userId") Integer userId);

    Integer selectRoleIdByName(@Param("name") String name);

    void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
