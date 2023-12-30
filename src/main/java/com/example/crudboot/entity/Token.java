package com.example.crudboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor
public class Token {
    
    private static final int EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expirationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TOKEN_USER"))
    private Users user;

    private String tokenType;

    public Token(Users user, String token, String tokenType) {
        super();

        this.user = user;
        this.token = token;
        this.tokenType = tokenType;
        this.expirationTime = calculateExpirationTime();
    }

    public Token(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationTime();
    }

    private LocalDateTime calculateExpirationTime() {
//        return LocalDateTime.now().plusHours(Token.EXPIRATION_TIME);

        return LocalDateTime.now().plusMinutes(Token.EXPIRATION_TIME);
    }

    public static boolean isValidToken(LocalDateTime expiryDate) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), expiryDate);
        return minutes >= 0;
    }

    public void updateToken(String code, String tokenType){
        this.token = code;
        this.tokenType = tokenType;
        this.expirationTime = calculateExpirationTime();
    }
}
