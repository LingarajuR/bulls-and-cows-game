package com.nsp.cowsandbullss.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsp.cowsandbullss.model.Score;
import com.nsp.cowsandbullss.model.User;
import com.nsp.cowsandbullss.repository.ScoreRepository;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    public Score saveScore(User user, int score) {
        Score newScore = new Score();
        newScore.setUser(user);
        newScore.setScore(score);
        return scoreRepository.save(newScore);
    }
}

