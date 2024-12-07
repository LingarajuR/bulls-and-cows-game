package com.nsp.cowsandbullss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nsp.cowsandbullss.model.Score;
import com.nsp.cowsandbullss.model.User;
import com.nsp.cowsandbullss.repository.ScoreRepository;
import com.nsp.cowsandbullss.service.ScoreService;

@SpringBootTest
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    @MockBean
    private ScoreRepository scoreRepository;

    @Test
    public void testSaveScore() {
        User user = new User();
        user.setUsername("testuser");

        Score score = new Score();
        score.setUser(user);
        score.setScore(100);

        Mockito.when(scoreRepository.save(Mockito.any(Score.class))).thenReturn(score);

        Score savedScore = scoreService.saveScore(user, 100);

        assertNotNull(savedScore);
        assertEquals(100, savedScore.getScore());
    }
}
