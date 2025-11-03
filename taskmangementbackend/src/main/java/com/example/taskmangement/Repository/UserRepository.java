package com.example.taskmangement.Repository;

import com.example.taskmangement.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    // Find a user by username
    UserModel findByLogin(String login);

    // Find a user by email address
    UserModel findByEmail(String email);

    // Custom query to find user by username or email (used during registration)
    @Query("SELECT u FROM UserModel u WHERE u.login = :login OR u.email = :email")
    UserModel findByLoginOrEmail(@Param("login") String login, @Param("email") String email);
}
