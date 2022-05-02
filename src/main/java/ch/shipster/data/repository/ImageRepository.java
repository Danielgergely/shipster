package ch.shipster.data.repository;

import ch.shipster.data.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Timo

@Repository
public interface ImageRepository extends JpaRepository<Address, Long> {
}
