package com.rohithkankipati.roommates.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rohithkankipati.roommates.dto.UserDTO;
import com.rohithkankipati.roommates.entity.UserEntity;
import com.rohithkankipati.roommates.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.mongodb.core.MongoTemplate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Creates a new user account.
     * @param user The user details.
     * @return The saved User object.
     */
    public UserDTO createAccount(UserDTO user) {
        // Implementation will be discussed.
        return null;
    }

   
    public UserDTO login(String identifier, String password) {
      
      Optional<UserEntity> userOptional = userRepository.findByUsernameOrEmailOrPhoneNumber(identifier);
      if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
          return userOptional.get().toUserDTO();
      }
      throw new IllegalArgumentException("Invalid username/email/phone number or password.");
    }

    /**
     * Changes the user's password.
     * @param userId The ID of the user.
     * @param oldPassword The current password.
     * @param newPassword The new password.
     * @return True if the password was successfully changed, false otherwise.
     */
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        // Implementation will be discussed.
        return false;
    }

    /**
     * Handles password reset requests.
     * @param email The email associated with the account.
     * @return True if the reset email was sent successfully, false otherwise.
     */
    public boolean forgetPassword(String email) {
        // Implementation will be discussed.
        return false;
    }

    /**
     * Retrieves the profile of a user.
     * @param userId The ID of the user.
     * @return The User object if found, null otherwise.
     */
    public UserDTO getProfile(String userId) {
        // Implementation will be discussed.
        return null;
    }

    /**
     * Handles user logout.
     * @param userId The ID of the user who is logging out.
     * @return True if logout was successful, false otherwise.
     */
    public boolean logout(String userId) {
        // Implementation will be discussed.
        return false;
    }
}
