package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.QuestionMapper;
import com.example.onlineeducationplatform.model.Question;
import com.example.onlineeducationplatform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Question> getQuestionsByQuizId(int quizId) {
        return questionMapper.selectQuestionsByQuizId(quizId);
    }

    @Override
    public Question getQuestionById(int id) {
        return questionMapper.selectQuestionById(id);
    }

    @Override
    public int createQuestion(Question question) {
        return questionMapper.insertQuestion(question);
    }

    @Override
    public int updateQuestion(Question question) {
        return questionMapper.updateQuestion(question);
    }

    @Override
    public int deleteQuestion(int id) {
        return questionMapper.deleteQuestion(id);
    }
}