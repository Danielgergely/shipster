package ch.shipster.service;

import ch.shipster.data.domain.Order;
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

    @Autowired
    OrderService orderService;

    @Autowired
    AddressService addressService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if (user.isEmpty()) {
            ShipsterLogger.logger.error("No user found for email: " + email);
            throw new NotFoundException("No user found for email: " + email);
        }
        return user.get();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            ShipsterLogger.logger.error("User with id: " + id + " not found");
            throw new NotFoundException("User with id: " + id + " not found");
        }
        return user.get();
    }

    public void createUser(User user) throws Exception {
        if (user.getUserId() == null) {
            if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
                ShipsterLogger.logger.error("Email address " + user.getEmail() + " already assigned another user.");
                throw new Exception("Email address " + user.getEmail() + " already assigned another user.");
            }
        } else if (userRepository.findByEmailAndUserIdNot(user.getEmail(), user.getUserId()) != null) {
            ShipsterLogger.logger.error("Email address " + user.getEmail() + " already assigned another user.");
            throw new Exception("Email address " + user.getEmail() + " already assigned another user.");
        }
        if (userRepository.findByUserNameIgnoreCase(user.getUserName()).isPresent()) {
            ShipsterLogger.logger.error("Username " + user.getUserName() + " already assigned another user.");
            throw new Exception("Username " + user.getUserName() + " already assigned another user.");
        }
        String userPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(userPassword));
        userRepository.save(user);
    }

    public void saveUser(User updatedUser) {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            throw new NotLoggedInException("User is not logged in");
        } else {
            currentUser.get().setFirstName(updatedUser.getFirstName());
            currentUser.get().setLastName(updatedUser.getLastName());
            currentUser.get().setUserName(updatedUser.getUserName());
            currentUser.get().setGender(updatedUser.getGender());

            userRepository.save(currentUser.get());
        }
    }

    public void updateUser(User updatedUser) {
        userRepository.save(updatedUser);
    }

    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            ShipsterLogger.logger.error("User with id " + userId + " not found.");
            throw new NotFoundException("User with id " + userId + " not found.");
        } else {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            for (Order order : orders) {
                orderService.deleteOrderById(order.getId());
            }
            addressService.deleteAddressById(user.get().getAddressId());
            userRepository.delete(user.get());
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

    public void changePassword(String password) {
        Optional<User> user = getCurrentUser();
        if (user.isEmpty()) {
            ShipsterLogger.logger.error("User is not logged in");
            throw new NotLoggedInException("User is not logged in");
        } else {
            String encodedPassword = passwordEncoder.encode(password);
            user.get().setPassword(encodedPassword);
            saveUser(user.get());
        }
    }

    public void changeUserPassword(String password, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            ShipsterLogger.logger.error("User not found");
            throw new NotFoundException("User not found");
        } else {
            String encodedPassword = passwordEncoder.encode(password);
            user.get().setPassword(encodedPassword);
            updateUser(user.get());
        }
    }
}
