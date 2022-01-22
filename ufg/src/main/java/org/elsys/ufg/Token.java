package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("email_tokens")
public class Token {

    @Id
    private Integer id;
    private String token;
    private String username;
    private String email;
}
