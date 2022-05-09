package ch.shipster.data.repository;

import ch.shipster.data.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Daniel
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
