package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("email_tokens")
public class EmailToken {
    @Id
    private Integer id;
    private String token;
    private Integer userId;
    private String timestamp;
    @Transient
    private Instant timer;

    public EmailToken(EmailTokenRepository emailTokenRepository, Integer userId){
        token = String.valueOf(UUID.randomUUID());
        while(emailTokenRepository.existsByToken(token) != null){
            token = String.valueOf(UUID.randomUUID());
        }

        this.userId = userId;
        timer = Instant.now();
        timestamp = timer.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getTimer() {
        return timer;
    }

    public void setTimer(Instant timer) {
        this.timer = timer;
    }
}
