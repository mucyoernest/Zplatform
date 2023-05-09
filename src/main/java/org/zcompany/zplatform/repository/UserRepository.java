package org.zcompany.zplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zcompany.zplatform.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(String token);


}
