package org.elsys.ufg;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT 1 FROM users WHERE username = :username AND password = :password LIMIT 1")
    Boolean existsByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT 1 FROM users WHERE username = :username LIMIT 1")
    Boolean existsByUsername(@Param("username") String username);

    @Query("SELECT 1 FROM users WHERE email = :email LIMIT 1")
    Boolean existsByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE users SET activated = 1 WHERE id = :id LIMIT 1")
    void activateById(@Param("id") Integer id);

    @Modifying
    @Query("DELETE FROM users WHERE id = :id LIMIT 1")
    void deleteById(@Param("id") Integer id);

    @Query("SELECT activated FROM users WHERE username = :username LIMIT 1")
    Boolean getActivatedStateByUsername(@Param("username") String username);
}