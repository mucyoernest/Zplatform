package org.zcompany.zplatform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcompany.zplatform.Service.UserService;
import org.zcompany.zplatform.model.User;
import org.zcompany.zplatform.repository.UserRepository;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // validate password (reference: https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/)
    public static boolean isValidPassword(String password) {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isValidEmail(String emailAddress) {
        String regexPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    //create a user
    @Override
    public User createUser(User user) {

        // Validate age
        LocalDate now = LocalDate.now();
        LocalDate dob = user.getDateOfBirth();
        int age = now.getYear() - dob.getYear();

        //get real age
        if (now.getMonthValue() < dob.getMonthValue() || (now.getMonthValue() == dob.getMonthValue() && now.getDayOfMonth() < dob.getDayOfMonth())) {
            age--;
        }

        //age less than 13
        if (age < 13) {
            throw new IllegalArgumentException("Age must be at least 13 years.");
        }
        //check password strength
        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException("A valid password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, must not contain any whitespace characters, and must be between 8 and 20 characters in length");
        }
        //check if email is valid
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Write a valid email");
        }
        // Encrypt password
        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        //set encrypted password and AccountStatus to unverified
        user.setPassword(encryptedPassword);
        user.setAccountStatus(User.AccountStatus.UNVERIFIED);

        //create user
        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setGender(user.getGender());
        user.setDateOfBirth(user.getDateOfBirth());
        user.setMaritalStatus(user.getMaritalStatus());
        user.setNationality(user.getNationality());
        user.setAccountStatus(User.AccountStatus.UNVERIFIED);
        user.setProfilePictureUrl(user.getProfilePictureUrl());

        // Save user
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }





}



