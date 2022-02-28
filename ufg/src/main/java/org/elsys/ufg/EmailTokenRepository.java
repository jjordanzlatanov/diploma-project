package org.elsys.ufg;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTokenRepository extends CrudRepository<EmailToken, Integer> {
    @Query("SELECT 1 FROM email_tokens WHERE token = :token LIMIT 1")
    Boolean existsByToken(@Param("token") String token);

    @Query("SELECT timestamp FROM email_tokens WHERE token = :token")
    String findTimestampByToken(@Param("token") String token);
}
