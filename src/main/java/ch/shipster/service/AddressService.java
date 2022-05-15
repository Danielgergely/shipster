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
            ShipsterLogger.logger.error("No address found for id: " + id);
            throw new NotFoundException("No address found for id: " + id);
        }
        return address.get();
    }

    public void createAddress(Address address) throws Exception {
        if (address.getAddressId() == null) {
            addressRepository.save(address);
        } else {
            saveAddress(address);
        }
    }

    public void updateAddress(Address updatedAddress) {
        addressRepository.save(updatedAddress);
    }

    public void saveAddress(Address updatedAddress) {
        Optional<User> currentUser = userService.getCurrentUser();
        if (currentUser.isEmpty()) {
            ShipsterLogger.logger.error("User is not logged in");
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
