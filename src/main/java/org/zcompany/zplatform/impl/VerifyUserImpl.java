package org.zcompany.zplatform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcompany.zplatform.Service.VerifyUser;
import org.zcompany.zplatform.exception.ResourceNotFoundException;
import org.zcompany.zplatform.model.User;
import org.zcompany.zplatform.repository.UserRepository;

@Service
public class VerifyUserImpl implements VerifyUser {

    @Autowired
    UserRepository userRepository;

    public User simulateVerificationCallback(Long id, boolean verify) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // Simulate the verification process
        boolean isVerified = verify; // Replace this with the actual verification result

        // Update the user's account status based on the verification result
        user.setId(id);
        user.setAccountStatus(isVerified? User.AccountStatus.VERIFIED : User.AccountStatus.UNVERIFIED);

        // Save the updated user
        return userRepository.save(user);

    }
}
