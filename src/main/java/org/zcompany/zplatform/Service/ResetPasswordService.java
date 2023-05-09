package org.zcompany.zplatform.Service;

import org.zcompany.zplatform.model.User;

public interface ResetPasswordService {

    String sendPasswordResetLink(String email);
    void resetPassword(String token, String newPassword);

    void updateUserPasswordByToken(String token, String password);

    User findUserByPasswordResetToken(String token);
}
