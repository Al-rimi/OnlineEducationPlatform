package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.OptionMapper;
import com.example.onlineeducationplatform.model.Option;
import com.example.onlineeducationplatform.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionMapper optionMapper;

    @Override
    public List<Option> getOptionsByQuestionId(int questionId) {
        return optionMapper.selectOptionsByQuestionId(questionId);
    }

    @Override
    public Option getOptionById(int id) {
        return optionMapper.selectOptionById(id);
    }

    @Override
    public int createOption(Option option) {
        return optionMapper.insertOption(option);
    }

    @Override
    public int updateOption(Option option) {
        return optionMapper.updateOption(option);
    }

    @Override
    public int deleteOption(int id) {
        return optionMapper.deleteOption(id);
    }
}