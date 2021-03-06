package ch.shipster.data.repository;

//Timo

import ch.shipster.data.domain.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {

    List<Cost> findByProviderId(Long providerId);

    List<Cost> findByPallet(int pallets);

    List<Cost> findByProviderIdAndPalletOrderByKmDesc(Long providerId, int pallets);

    List<Cost> findAllByProviderIdAndKmAndPallet(Long providerId, int km, int pallet);
}
