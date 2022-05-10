package ch.shipster.controller;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.exceptions.NoPermissionException;
import ch.shipster.security.ShipsterUserRole;
import ch.shipster.service.AddressService;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            Address address = addressService.findAddressById(user.getAddressId());
            model.addAttribute("currentUser", adminUser.get());
            model.addAttribute("user", user);
            model.addAttribute("address", address);
            return "admin/user";
        }
    }

    @PostMapping("admin/updateProfile")
    public String updateProfile(@RequestParam Long userId, @ModelAttribute User updatedUser, Address updatedAddress, RedirectAttributes redirectAttributes) throws Exception {
        User userToUpdate = userService.findById(userId);
        userToUpdate.setUserName(updatedUser.getUserName());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setGender(updatedUser.getGender());
        userService.updateUser(userToUpdate);
        Address addressToUpdate = addressService.findAddressById(userToUpdate.getAddressId());
        addressToUpdate.setStreet(updatedAddress.getStreet());
        addressToUpdate.setCity(updatedAddress.getCity());
        addressToUpdate.setCountry(updatedAddress.getCountry());
        addressToUpdate.setNumber(updatedAddress.getNumber());
        addressToUpdate.setZip(updatedAddress.getZip());
        addressService.updateAddress(addressToUpdate);
        //redirectAttributes.addAttribute("message", "Profile has been updated.");
        return "redirect:user?userId=" + userToUpdate.getUserId();
    }

    @PostMapping("admin/updatePassword")
    public String updatePassword(@RequestParam(name = "password") String password, @RequestParam Long userId, RedirectAttributes redirectAttributes) throws Exception {
        userService.changeUserPassword(password, userId);
        redirectAttributes.addAttribute("message", "Password has been changed.");
        return "redirect:/profile";
    }

    @GetMapping("admin/role")
    public String addRemoveAdminRole(@RequestParam Long userId, Model model) throws Exception {
        Optional<User> adminUser = userService.getCurrentUser();
        if (adminUser.isEmpty()) {
            return "user/login";
        } else {
            User user = userService.findById(userId);
            if(user.getRoles().contains("ADMIN")){
                user.setRole("USER");
            } else {
                user.setRoles(ShipsterUserRole.ADMIN);
            }
            userService.updateUser(user);
            model.addAttribute("currentUser", adminUser.get());
            model.addAttribute("user", user);
            return "redirect:user?userId=" + userId;
        }
    }

    @GetMapping("admin/user/delete")
    public String deleteUser(@RequestParam Long userId, Model model) throws Exception {
        Optional<User> adminUser = userService.getCurrentUser();
        if (adminUser.isEmpty()) {
            return "user/login";
        } else if (!adminUser.get().getRoles().contains("ADMIN")) {
            throw new NoPermissionException("You can not delete this user. You are not an admin");
        } else {
            userService.deleteUser(userId);
            List<User> users = userService.getAllUsers();
            model.addAttribute("currentUser", adminUser.get());
            model.addAttribute("users", users);
            model.addAttribute("newUser", new User());
            model.addAttribute("newAddress", new Address());
            return "admin/users";
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
