package org.zcompany.zplatform.controller;

import com.cloudinary.Cloudinary;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zcompany.zplatform.Service.FileUpload;
import org.zcompany.zplatform.Service.UserService;
import org.zcompany.zplatform.Service.VerifyUser;
import org.zcompany.zplatform.model.User;

import java.io.IOException;
import java.util.Map;

@Api(tags = "User Management")
@RestController
@RequestMapping("api/")
public class UserController {

    // Injects instances
    @Autowired
    private UserService userService;

    @Autowired FileUpload fileUpload;

    @Autowired
    VerifyUser verifyUser;

    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User created successfully"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 400, message = "Bad Request - IllegalArgumentException"),
            @ApiResponse(code = 403, message = "Forbidden access"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    //method to handle POST requests
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Delegate the user creation logic to the UserService
        User createdUser = userService.createUser(user);
        // Returns a HTTP 201 (Created)
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //handle user updates
    @ApiOperation(value = "Update user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 400, message = "Bad Request - IllegalArgumentException"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 403, message = "Forbidden access"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })

    //Update user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //delete user

    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User deleted successfully"), })

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Upload profile picture")
    @ApiParam(value = "File to upload", required = true)
    @PostMapping("/users/{id}/upload-profile")
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        userService.uploadProfilePicture(id, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Request verification user")
    @PostMapping("/users/{id}/verify")
    public ResponseEntity<Void> requestVerification(
            @PathVariable Long id,
            @RequestParam String nidOrPassport,
            @RequestParam MultipartFile documentImage) throws IOException {
        String documentImageUrl = fileUpload.uploadImage(documentImage);
        userService.verifyUser(id, nidOrPassport, documentImageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Approve or deny user verification request")
    @PostMapping("/users/verify/{id}")
    public ResponseEntity<Void> verifyResponse(
            @PathVariable Long id,
            @RequestParam Boolean response)
    {
        verifyUser.simulateVerificationCallback(id,response);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
