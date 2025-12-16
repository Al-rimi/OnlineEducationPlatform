package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PurchaseMapper {
    int insert(Purchase purchase);

    Purchase select(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    List<Purchase> selectByUser(@Param("userId") Integer userId);
}
