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


    public String sendPasswordResetLink(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        LocalDateTime tokenExpiry = LocalDateTime.now().plusHours(1); // Token valid for 1 hour

        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiry(tokenExpiry);
        userRepository.save(user);
        String resetLink = "http://127.0.01:8080/reset/reset-password?token=" + token;
        String subject = "ZPlatform - Password Reset";
        String text = "To reset your password, click the link below:\n"
                + resetLink;


        emailService.sendEmail(user.getEmail(), subject, text);

        // return reset lnk
        return resetLink;

//        sendResetLinkEmail(user.getEmail(), resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid password reset token"));

        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token has expired");
        }

        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        userRepository.save(user);
    }


    public User findUserByPasswordResetToken(String token) {
        return userRepository.findByPasswordResetToken(token).orElseThrow(() -> new ResourceNotFoundException("Invalid password reset token"));
    }

    public void updateUserPasswordByToken(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid password reset token"));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        userRepository.save(user);
    }


//    @Override
//    public void setProfilePicture(Long id, String profilePictureUrl) {
//        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//        user.setProfilePicture(profilePictureUrl);
//        userRepository.save(user);
//    }



}
