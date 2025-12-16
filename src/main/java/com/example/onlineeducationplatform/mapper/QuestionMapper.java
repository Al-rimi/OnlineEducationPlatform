package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.Question;
import java.util.List;

public interface QuestionMapper {
    List<Question> selectQuestionsByQuizId(int quizId);

    Question selectQuestionById(int id);

    int insertQuestion(Question question);

    int updateQuestion(Question question);

    int deleteQuestion(int id);
}