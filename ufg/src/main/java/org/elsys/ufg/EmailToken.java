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
    private Integer user_id;
    private String timestamp;

    public EmailToken(EmailTokenRepository emailTokenRepository, Integer userId){
        token = String.valueOf(UUID.randomUUID());
        while(emailTokenRepository.existsByToken(token) != null){
            token = String.valueOf(UUID.randomUUID());
        }

        this.user_id = userId;
        this.timestamp = Instant.now().toString();
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
        return user_id;
    }

    public void setUserId(Integer userId) {
        this.user_id = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
