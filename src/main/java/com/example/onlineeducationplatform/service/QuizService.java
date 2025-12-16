package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Quiz;
import java.util.List;

public interface QuizService {
    List<Quiz> getAllQuizzes();

    List<Quiz> getQuizzesByCourseId(int courseId);

    Quiz getQuizById(int id);

    int createQuiz(Quiz quiz);

    int updateQuiz(Quiz quiz);

    int deleteQuiz(int id);
}