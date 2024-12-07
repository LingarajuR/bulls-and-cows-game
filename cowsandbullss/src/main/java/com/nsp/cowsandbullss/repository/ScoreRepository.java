package com.nsp.cowsandbullss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsp.cowsandbullss.model.Score;
import com.nsp.cowsandbullss.model.User;

public interface ScoreRepository extends JpaRepository<Score, Long> {
	List<Score> findByUser(User user);
}

