package com.example.onlineeducationplatform.service;

import com.example.onlineeducationplatform.model.QuizResult;
import java.util.List;

public interface QuizResultService {
    List<QuizResult> getResultsByQuizId(int quizId);

    QuizResult getResultByQuizAndUser(int quizId, int userId);

    QuizResult getResultById(int id);

    int saveResult(QuizResult result);

    int updateResult(QuizResult result);

    int deleteResult(int id);
}