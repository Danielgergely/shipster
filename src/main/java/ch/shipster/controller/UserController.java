package ch.shipster.controller;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.exceptions.NotFoundException;
import ch.shipster.service.AddressService;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String registerUser(@ModelAttribute User user, Address address) throws Exception {
        Address newAddress = new Address(
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getZip(),
                address.getCountry());
        addressService.saveAddress(newAddress);
        user.setAddressId(newAddress.getAddressId());
        User newUser = new User(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddressId(),
                user.getGender());
        userService.saveUser(newUser);
        return "user/login";
    }

    @GetMapping("profile")
    public String getProfileView(Model model) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isEmpty()) {
            return "user/login";
        } else {
            Address address = addressService.findAddressById(user.get().getAddressId());
            model.addAttribute("user", user.get());
            model.addAttribute("address", address);
            return "user/userProfile";
        }
    }

    @PostMapping("updateProfile")
    public String updateProfile(@ModelAttribute User updatedUser, Address updatedAddress) throws Exception {
        userService.saveUser(updatedUser);
        addressService.saveAddress(updatedAddress);
        return "redirect:profile";
    }

    @PostMapping("updatePassword")
    public String updatePassword(@RequestParam(name = "password") String password) throws Exception {
        userService.changePassword(password);
        return "redirect:profile";
    }
}
