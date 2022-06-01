package ch.shipster.controller;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.exceptions.NotFoundException;
import ch.shipster.service.AddressService;
import ch.shipster.service.ShipsterLogger;
import ch.shipster.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


// Daniel

@Controller
@RequestMapping(path = "/")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @PostMapping("register")
    public String registerUser(@ModelAttribute User user, Address address, RedirectAttributes redirectAttributes) throws Exception {
        Address newAddress = new Address(
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getZip(),
                address.getCountry());
        boolean valid = addressService.validateAddress(newAddress);
        if (!valid) {
            redirectAttributes.addAttribute("message", "Address is not valid. Please use a valid address");
            return "redirect:register";
        }
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
        try {
            userService.createUser(newUser);
            ShipsterLogger.logger.info("User with ID " + newAddress.getAddressId() + " has successfully registered");
            return "user/login";
        }
        catch(Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
            return "redirect:register";
        }
    }

    @GetMapping("profile")
    public String getProfileView(@RequestParam(required = false, name = "message") String message, Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Address address = addressService.findAddressById(user.get().getAddressId());
            model.addAttribute("user", user.get());
            model.addAttribute("address", address);
            if (message != null) {
                if (message.equals("Your profile has been updated.")) {
                    model.addAttribute("profile_update", message);
                } else {
                    model.addAttribute("password_changed", message);
                }
            }
            return "user/userProfile";
        }
    }

    @PostMapping("updateProfile")
    public String updateProfile(@ModelAttribute User updatedUser, Address updatedAddress, RedirectAttributes redirectAttributes) throws Exception {
        userService.saveUser(updatedUser);
        addressService.saveAddress(updatedAddress);
        ShipsterLogger.logger.info("User " + userService.getCurrentUser() + " changed his address");
        redirectAttributes.addAttribute("message", "Your profile has been updated.");
        return "redirect:/profile";
    }

    @PostMapping("updatePassword")
    public String updatePassword(@RequestParam(name = "password") String password, RedirectAttributes redirectAttributes) throws Exception {
        userService.changePassword(password);
        ShipsterLogger.logger.info("User " + userService.getCurrentUser() + " changed his password");
        redirectAttributes.addAttribute("message", "Your password has been changed.");
        return "redirect:/profile";
    }
}
