package ch.shipster.service;

import ch.shipster.data.domain.Address;
import ch.shipster.data.repository.AddressRepository;
import ch.shipster.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address findAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NotFoundException("No address found for id: " + id);
        }
        return address.get();
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
}
