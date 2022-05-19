package ch.shipster.data.repository;

import ch.shipster.data.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Daniel
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressById(String id);
    List<Address> findAllByStreetAndNumberAndZipAndCountry(
            String street, String number, String zip, String country);

}
