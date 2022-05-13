package ch.shipster.data.repository;

//Timo

import ch.shipster.data.domain.Cost;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {

    // Not necessary, already done by the JpaRepository
    //@Override
    //Optional<Cost> findById(Long costId);

    //@Override
    //List<Cost> findAll();

    //@Override
    //Cost save(Cost cost);

    List<Cost> findByProviderId(Long providerId);

    List<Cost> findByPallet(int pallets);

    List<Cost> findByProviderIdAndPalletOrderByKmDesc(Long providerId, int pallets);

}
