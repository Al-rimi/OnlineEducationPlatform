package com.example.onlineeducationplatform.service.impl;

import com.example.onlineeducationplatform.mapper.QuizMapper;
import com.example.onlineeducationplatform.model.Quiz;
import com.example.onlineeducationplatform.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizMapper quizMapper;

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizMapper.selectAllQuizzes();
    }

    @Override
    public List<Quiz> getQuizzesByCourseId(int courseId) {
        return quizMapper.selectQuizzesByCourseId(courseId);
    }

    @Override
    public Quiz getQuizById(int id) {
        return quizMapper.selectQuizById(id);
    }

    @Override
    public int createQuiz(Quiz quiz) {
        return quizMapper.insertQuiz(quiz);
    }

    @Override
    public int updateQuiz(Quiz quiz) {
        return quizMapper.updateQuiz(quiz);
    }

    @Override
    public int deleteQuiz(int id) {
        return quizMapper.deleteQuiz(id);
    }
}