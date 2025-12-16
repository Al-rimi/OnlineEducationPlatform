package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.Question;
import java.util.List;

public interface QuestionService {
    List<Question> getQuestionsByQuizId(int quizId);

    Question getQuestionById(int id);

    int createQuestion(Question question);

    int updateQuestion(Question question);

    int deleteQuestion(int id);
}