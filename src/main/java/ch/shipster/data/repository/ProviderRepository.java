package ch.shipster.data.repository;

import ch.shipster.data.domain.Provider;
import ch.shipster.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Timo

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> getAllByName(String name);
}
