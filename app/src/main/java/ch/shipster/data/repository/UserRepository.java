package ch.shipster.data.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ch.shipster.data.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailIgnoreCase(String email);

    User findByEmailAndIdNot(String email, Long userId);

    User findByEmail(String email);

    Optional<User> findByUsernameIgnoreCase(String username);
}
