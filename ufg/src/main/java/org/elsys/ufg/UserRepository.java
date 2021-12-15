package org.elsys.ufg;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT 1 FROM users WHERE username = :username AND password = :password LIMIT 1")
    boolean existsByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
