package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Quiz;
import java.util.List;

public interface QuizMapper {
    List<Quiz> selectAllQuizzes();

    List<Quiz> selectQuizzesByCourseId(int courseId);

    Quiz selectQuizById(int id);

    int insertQuiz(Quiz quiz);

    int updateQuiz(Quiz quiz);

    int deleteQuiz(int id);
}