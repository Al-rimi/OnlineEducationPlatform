package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Option;
import java.util.List;

public interface OptionService {
    List<Option> getOptionsByQuestionId(int questionId);

    Option getOptionById(int id);

    int createOption(Option option);

    int updateOption(Option option);

    int deleteOption(int id);
}