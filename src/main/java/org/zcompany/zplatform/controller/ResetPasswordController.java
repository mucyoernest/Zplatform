package org.zcompany.zplatform.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.zcompany.zplatform.Service.ResetPasswordService;
import org.zcompany.zplatform.model.User;

@RestController
@RequestMapping("reset")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService passwordService;

    @PostMapping("/send-reset-link")
    public ResponseEntity<String> sendPasswordResetLink(@RequestParam("email") String email) {
        String resetLink = passwordService.sendPasswordResetLink(email);
        return ResponseEntity.ok(resetLink);
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<Void> resetPassword(@RequestParam("token") String token,
//                                              @RequestParam("newPassword") String newPassword) {
//        userService.resetPassword(token, newPassword);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/reset-password")
    public ModelAndView showResetPasswordForm(@RequestParam("token") String token) {
        // Check if the token exists and is valid
        User user = passwordService.findUserByPasswordResetToken(token);
        if (user == null) {
            return new ModelAndView("error", "message", "Invalid password reset token");
        }

        // Create a ModelAndView with the reset-password form and the token
        ModelAndView modelAndView = new ModelAndView("reset-password");
        modelAndView.addObject("token", token);
        return modelAndView;
    }

    @PostMapping("/update-password")
    public ModelAndView updatePassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       @RequestParam("passwordConfirm") String passwordConfirm) {
        // Check if the passwords match
        if (!password.equals(passwordConfirm)) {
            return new ModelAndView("error", "message", "Passwords do not match");
        }

        // Update the user's password
        passwordService.updateUserPasswordByToken(token, password);

        // Redirect to the login page or show a success message
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("message", "Your password has been updated successfully!");
        return modelAndView;
    }



}
