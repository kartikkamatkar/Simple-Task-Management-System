package com.example.taskmangement.Service;

import com.example.taskmangement.Model.UserModel;
import com.example.taskmangement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Repository to interact with the user table in the database
    private final UserRepository userRepository;

    // Password encoder to securely hash passwords
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        // Injecting repository and password encoder using constructor injection
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to register a new user
    public UserModel register(String login, String password, String email) {

        // Check if a user already exists with the same username or email
        UserModel existingUser = userRepository.findByLoginOrEmail(login, email);
        if (existingUser != null) {
            // If yes, return null so controller can show error
            return null;
        }

        // Create a new user object
        UserModel user = new UserModel();
        user.setLogin(login);
        user.setEmail(email);

        // Encode password before saving for security
        user.setPassword(passwordEncoder.encode(password));

        // Save user in the database and return it
        return userRepository.save(user);
    }

    // Method to authenticate a user during login
    public UserModel authentication(String login, String rawPassword) {

        // Fetch user by username
        UserModel user = userRepository.findByLogin(login);

        // Validate user exists and password matches encrypted password
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }

        // If user not found or password incorrect return null
        return null;
    }
}
