package ch.shipster.controller;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.service.AddressService;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Daniel

@Controller
@RequestMapping(path = "/")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping("admin")
    public String getAdminView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            model.addAttribute("user", user.get());
            return "admin/admin";
        }
    }

    @GetMapping("admin/users")
    public String getUsersAdminView(Model model) {
        Optional<User> user = userService.getCurrentUser();

        if (user.isEmpty()) {
            return "user/login";
        } else {
            List<User> users = userService.getAllUsers();
            model.addAttribute("currentUser", user.get());
            model.addAttribute("users", users);
            model.addAttribute("newUser", new User());
            model.addAttribute("newAddress", new Address());
            return "admin/users";
        }
    }

    @GetMapping("admin/user")
    public String getUserAdminView(@RequestParam Long userId, Model model) {
        Optional<User> adminUser = userService.getCurrentUser();
        if (adminUser.isEmpty()) {
            return "user/login";
        } else {
            User user = userService.findById(userId);
            model.addAttribute("currentUser", adminUser.get());
            model.addAttribute("user", user);
            return "admin/user";
        }
    }

    @GetMapping("admin/orders")
    public String getOrderAdminView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            model.addAttribute("user", user.get());
            return "admin/orders";
        }
    }

    @PostMapping("admin/createUser")
    public String registerUser(@ModelAttribute User user, Address address) throws Exception {
        Address newAddress = new Address(
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getZip(),
                address.getCountry());
        addressService.createAddress(newAddress);
        user.setAddressId(newAddress.getAddressId());
        User newUser = new User(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddressId(),
                user.getGender());
        userService.createUser(newUser);
        return "redirect:/admin/users";
    }
}
