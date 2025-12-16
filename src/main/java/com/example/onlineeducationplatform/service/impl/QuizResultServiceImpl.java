package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.QuizResultMapper;
import com.example.onlineeducationplatform.model.QuizResult;
import com.example.onlineeducationplatform.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuizResultServiceImpl implements QuizResultService {
    @Autowired
    private QuizResultMapper resultMapper;

    @Override
    public List<QuizResult> getResultsByQuizId(int quizId) {
        return resultMapper.selectResultsByQuizId(quizId);
    }

    @Override
    public QuizResult getResultByQuizAndUser(int quizId, int userId) {
        return resultMapper.selectResultByQuizAndUser(quizId, userId);
    }

    @Override
    public QuizResult getResultById(int id) {
        return resultMapper.selectResultById(id);
    }

    @Override
    public int saveResult(QuizResult result) {
        return resultMapper.insertResult(result);
    }

    @Override
    public int updateResult(QuizResult result) {
        return resultMapper.updateResult(result);
    }

    @Override
    public int deleteResult(int id) {
        return resultMapper.deleteResult(id);
    }
}