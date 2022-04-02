package ch.shipster.controller;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.service.AddressService;
import ch.shipster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


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

    @GetMapping("/profile/edit")
    public String getEditProfileView() {
        return "user/profileEdit.html";
    }
}
