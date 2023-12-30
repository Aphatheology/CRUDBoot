package com.example.crudboot.repository;

import com.example.crudboot.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByTokenAndTokenType(String token, String tokenType);
}
