package org.zcompany.zplatform.Service;

import org.springframework.web.multipart.MultipartFile;
import org.zcompany.zplatform.model.User;

public interface UserService {
    //user signup
    User createUser(User user);

    //update user details
    User updateUser(User user, Long id);

    void deleteUser(Long id);

    void uploadProfilePicture(Long id, MultipartFile image);

    void verifyUser(Long id, String nidOrPassport, String documentImageUrl);
}
