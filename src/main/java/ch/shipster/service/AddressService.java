package ch.shipster.service;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.data.repository.AddressRepository;
import ch.shipster.exceptions.NotFoundException;
import ch.shipster.exceptions.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Daniel

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserService userService;

    public Address findAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NotFoundException("No address found for id: " + id);
        }
        return address.get();
    }

    public void saveAddress(Address updatedAddress) {
        Optional<User> currentUser = userService.getCurrentUser();
        if (currentUser.isEmpty()) {
            throw new NotLoggedInException("User is not logged in");
        } else {
            Address address = findAddressById(currentUser.get().getAddressId());

            address.setStreet(updatedAddress.getStreet());
            address.setNumber(updatedAddress.getNumber());
            address.setCity(updatedAddress.getCity());
            address.setZip(updatedAddress.getZip());
            address.setCountry(updatedAddress.getCountry());
            addressRepository.save(address);
        }
    }
}
