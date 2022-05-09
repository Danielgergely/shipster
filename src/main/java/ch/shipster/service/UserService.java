package ch.shipster.service;

import ch.shipster.data.domain.User;
import ch.shipster.data.repository.UserRepository;
import ch.shipster.exceptions.NotFoundException;
import ch.shipster.exceptions.NotLoggedInException;
import ch.shipster.security.authentication.ShipsterUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Daniel

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if (user.isEmpty()) {
            throw new NotFoundException("No user found for email: " + email);
        }
        return user.get();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User with id: " + id + " not found");
        }
        return user.get();
    }

    public void createUser(User user) throws Exception {
        if (user.getUserId() == null) {
            if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
                throw new Exception("Email address " + user.getEmail() + " already assigned another user.");
            }
        } else if (userRepository.findByEmailAndUserIdNot(user.getEmail(), user.getUserId()) != null) {
            throw new Exception("Email address " + user.getEmail() + " already assigned another user.");
        }
        if (userRepository.findByUserNameIgnoreCase(user.getUserName()).isPresent()) {
            throw new Exception("Username " + user.getUserName() + " already assigned another user.");
        }
        String userPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(userPassword));
        userRepository.save(user);
    }

    public void saveUser(User updatedUser) throws Exception {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            throw new NotLoggedInException("User is not logged in");
        } else {
            currentUser.get().setFirstName(updatedUser.getFirstName());
            currentUser.get().setLastName(updatedUser.getLastName());
            currentUser.get().setUserName(updatedUser.getUserName());
            currentUser.get().setEmail(updatedUser.getEmail());
            currentUser.get().setGender(updatedUser.getGender());

            userRepository.save(currentUser.get());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != "anonymousUser") {
            String userEmail = ((ShipsterUserDetails) user).getEmail();
            return userRepository.findByEmailIgnoreCase(userEmail);
        } else {
            return Optional.empty();
        }
    }

    public void changePassword(String password) throws Exception {
        Optional<User> user = getCurrentUser();
        if (user.isEmpty()){
            throw new NotLoggedInException("User is not logged in");
        } else {
            String encodedPassword = passwordEncoder.encode(password);
            user.get().setPassword(encodedPassword);
            saveUser(user.get());
        }
    }
}
