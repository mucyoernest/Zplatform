package org.zcompany.zplatform.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zcompany.zplatform.Service.UserService;
import org.zcompany.zplatform.model.User;

@Api(tags = "User Management")
@RestController
@RequestMapping("api/")
public class UserController {

    // Injects instances
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully"),
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
}
