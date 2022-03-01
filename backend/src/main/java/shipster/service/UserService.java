package shipster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import shipster.data.domain.Role;
import shipster.data.domain.User;
import shipster.data.repository.RoleRepository;
import shipster.data.repository.UserRepository;
import shipster.exceptions.NoPermissionException;
import shipster.exceptions.NotFoundException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


// Daniel
// Some methods inspired by our Internet Technology project in semester 3
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ShipsterUserDetailsService userDetailsService;

    public User createUser(@Valid User user) throws Exception {
        if (user.getId() == null) {
            if (userRepository.findByEmailIgnoreCase(user.getEmail()) != null) {
                throw new Exception("Email address " + user.getEmail() + " is already assigned to a user.");
            }
        } else if (userRepository.findByEmailAndIdNot(user.getEmail(), user.getId()) != null) {
            throw new Exception("Email address " + user.getEmail() + " is already assigned to a user");
        }
        if (userRepository.findByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            throw new Exception("Username " + user.getUsername() + " already taken by another user.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No user with ID " + id + " found.");
        } else if (!hasPermission(user.get())) {
            throw new NoPermissionException("You don't have permission to view user with ID " + id + ".");
        }
        return user.get();
    }

    public User editUser(Long id, User user) throws NotFoundException, NoPermissionException {
        Optional<User> currentUser = userRepository.findById(id);
        if (currentUser.isEmpty()) {
            throw new NotFoundException("No user with ID " + id + " found.");
        } else if (!currentUser.get().getId().equals(getCurrentUser().getId())) {
            throw new NoPermissionException("You don't have permission to edit other users.");
        }
        currentUser.get().setFirstName(user.getFirstName());
        currentUser.get().setLastName(user.getLastName());
        currentUser.get().setUsername(user.getUsername());

        return userRepository.save(currentUser.get());
    }

    public void deleteUser(User user) throws NoPermissionException {
        // delete orders and other items related to the user
        if (isAdmin() || user.getId().equals(getCurrentUser().getId())) {
            userRepository.delete(user);
        } else {
            throw new NoPermissionException("You don't have permission to delete other users.");
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public User findById(Long id) throws NotFoundException, NoPermissionException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No user with ID " + id + " found");
        } else if (!hasPermission(user.get())) {
            throw new NoPermissionException("You don't have permission to view user with ID " + id + ".");
        }
        return user.get();
    }

    public boolean usernameAvailable(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    // https://stackoverflow.com/questions/31159075/how-to-find-out-the-currently-logged-in-user-in-spring-boot
    public User getCurrentUser() {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmailIgnoreCase(userEmail);
    }

    public User findUserByUserId(Long userId) throws NotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("No user with ID" + userId + " found.");
        }
        return user.get();
    }

    private boolean hasPermission(User user) {
        return getCurrentUser().getId().equals(user.getId()) || isAdmin();
    }

    private boolean isAdmin() {
        boolean isAdmin = false;
        User user = findUserByUserId(getCurrentUser().getId());
        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());
        if (details != null && details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            isAdmin = true;
        }
        return isAdmin;
    }


}
