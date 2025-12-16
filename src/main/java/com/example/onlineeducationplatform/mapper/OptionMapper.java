package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Option;
import java.util.List;

public interface OptionMapper {
    List<Option> selectOptionsByQuestionId(int questionId);

    Option selectOptionById(int id);

    int insertOption(Option option);

    int updateOption(Option option);

    int deleteOption(int id);
}