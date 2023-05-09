package org.zcompany.zplatform.impl;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zcompany.zplatform.Service.FileUpload;
import org.zcompany.zplatform.Service.UserService;
import org.zcompany.zplatform.exception.ResourceNotFoundException;
import org.zcompany.zplatform.model.User;
import org.zcompany.zplatform.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
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

    @Autowired
    private FileUpload fileUpload;



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

    public static boolean isAgeValid(LocalDate dob){
        // Validate age
        LocalDate now = LocalDate.now();
        int age = now.getYear() - dob.getYear();

        //get real age
        if (now.getMonthValue() < dob.getMonthValue() || (now.getMonthValue() == dob.getMonthValue() && now.getDayOfMonth() < dob.getDayOfMonth())) {
            age--;
        }

        return (age>13);
    }

    //create a user
    @Override
    public User createUser(User user) {



        //age less than 13
        if (!isAgeValid(user.getDateOfBirth())) {
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

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("User already Exists");
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
        user.setProfilePictureUrl(null);
        user.setNidOrPassport(null);


        // Save user
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public User updateUser(User user, Long id) {
        // Find the user to be updated
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        //age less than 13
        if (!isAgeValid(user.getDateOfBirth())) {
            throw new IllegalArgumentException("Age must be at least 13 years.");
        }
        //check if email is valid
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Write a valid email");
        }

        //update user
        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setGender(user.getGender());
        user.setDateOfBirth(user.getDateOfBirth());
        user.setMaritalStatus(user.getMaritalStatus());
        user.setNationality(user.getNationality());
        user.setAccountStatus(User.AccountStatus.UNVERIFIED);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        // Find the user to be deleted
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    //Swagger conf
    @ApiOperation(value = "Upload a file")

    //Method to upload a profile picture and get url from Cloudinary
    @Override
    public void uploadProfilePicture(Long id, MultipartFile image) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        // Find the user to be deleted
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        try {
            String imageUrl = fileUpload.uploadImage(image);
            user.setProfilePictureUrl(imageUrl);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile picture", e);
        }
    }


    @Override
    public void verifyUser(Long id, String nidOrPassport, String documentImageUrl) {
        // Retrieve the user by ID
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update the user's NID or Passport, document image URL, account status, and callback URL
        user.setNidOrPassport(nidOrPassport);
        user.setDocumentImageUrl(documentImageUrl);
        user.setAccountStatus(User.AccountStatus.PENDING_VERIFICATION);

        // Save the updated user
        userRepository.save(user);

    }
















}



