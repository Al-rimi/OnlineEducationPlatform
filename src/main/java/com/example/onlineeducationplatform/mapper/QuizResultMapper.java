package com.example.onlineeducationplatform.mapper;

import com.example.onlineeducationplatform.model.QuizResult;
import java.util.List;

public interface QuizResultMapper {
    List<QuizResult> selectResultsByQuizId(int quizId);

    QuizResult selectResultByQuizAndUser(int quizId, int userId);

    QuizResult selectResultById(int id);

    int insertResult(QuizResult result);

    int updateResult(QuizResult result);

    int deleteResult(int id);
}