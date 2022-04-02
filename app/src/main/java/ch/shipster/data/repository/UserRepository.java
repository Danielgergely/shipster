package ch.shipster.data.repository;

import ch.shipster.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Daniel

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByUserName(String userName);

    User findByEmail(String email);

    User findByEmailAndUserIdNot(String email, Long userId);

    Optional<User> findByUserNameIgnoreCase(String username);

}
