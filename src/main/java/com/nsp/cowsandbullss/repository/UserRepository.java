package com.nsp.cowsandbullss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsp.cowsandbullss.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
