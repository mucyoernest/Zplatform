package org.zcompany.zplatform.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zcompany.zplatform.Service.EmailService;
import org.zcompany.zplatform.Service.ResetPasswordService;
import org.zcompany.zplatform.exception.ResourceNotFoundException;
import org.zcompany.zplatform.model.User;
import org.zcompany.zplatform.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResetPasswordImpl implements ResetPasswordService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    // Sends a password reset link to the given email address
    public String sendPasswordResetLink(String email) {
        // Find the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // Generate a unique token for the password reset link
        String token = UUID.randomUUID().toString();
        LocalDateTime tokenExpiry = LocalDateTime.now().plusHours(1); // Token valid for 1 hour

        // Save the token and expiry time to the user record
        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiry(tokenExpiry);
        userRepository.save(user);
        // Create the password reset link using the token
        String resetLink = "http://127.0.01:8080/reset/reset-password?token=" + token;
        String subject = "ZPlatform - Password Reset";
        String text = "To reset your password, click the link below:\n"
                + resetLink;

        // Create the password reset link using the token
        emailService.sendEmail(user.getEmail(), subject, text);

        // return reset lnk
        return resetLink;
    }

    // Resets the user's password using the given token and new password
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid password reset token"));

        // Check if the token has expired
        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token has expired");
        }

        // Update the user's password and clear the token and expiry
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        userRepository.save(user);
    }

    // Finds a user by the password reset token
    public User findUserByPasswordResetToken(String token) {
        return userRepository.findByPasswordResetToken(token).orElseThrow(() -> new ResourceNotFoundException("Invalid password reset token"));
    }

    // Updates the user's password using the given token and new password

    public void updateUserPasswordByToken(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid password reset token"));

        // Encode the new password and update the user record
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        userRepository.save(user);
    }
}
