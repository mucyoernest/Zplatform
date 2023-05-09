package org.zcompany.zplatform.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zcompany.zplatform.model.User;
import org.zcompany.zplatform.repository.UserRepository;

public interface VerifyUser {
    public User simulateVerificationCallback(Long id, boolean verify);
}
