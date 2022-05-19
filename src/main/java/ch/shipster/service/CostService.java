package ch.shipster.service;

import ch.shipster.data.domain.Cost;
import ch.shipster.data.domain.Provider;
import ch.shipster.data.repository.CostRepository;
import ch.shipster.data.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CostService {

    @Autowired
    CostRepository costRepository;

    @Autowired
    ProviderRepository providerRepository;

    public Cost getCostById(Long costId){
        return costRepository.getById(costId);
    }

    public List<Cost> getCosts(int km, int pallets) {
        List<Cost> inCost = costRepository.findByPallet(pallets);
        List<Long> providerIds = new ArrayList<Long>();
        List<Cost> outCost = new ArrayList<Cost>();

        for (Cost c : inCost) {
            if (!(providerIds.contains(c.getProviderId()))) {
                providerIds.add(c.getProviderId());
            }
        }

        for (Provider p : providerRepository.findAllById(providerIds)) {
            outCost.add(getCost(p.getId(), km, pallets));
        }

        return outCost;
    }

    public Cost getCost(Long providerId, int km, int pallets) {
        for (Cost c : costRepository.findByProviderIdAndPalletOrderByKmDesc(providerId, pallets)) {
            if (c.getKm() > km) {
                return c;
            }
        }
        return null;
    }

    public Cost getCheapestCost(int km, int pallets) {
        List<Cost> inCost = getCosts(km, pallets);
        Cost outCost = inCost.get(0);

        for (Cost c : getCosts(km, pallets)) {
            if (c.getPrice() < outCost.getPrice()) {
                outCost = c;
            }
        }

        return outCost;
    }

    public Cost getMostExpensiveCost(int km, int pallets) {
        List<Cost> inCost = getCosts(km, pallets);
        Cost outCost = inCost.get(0);

        for (Cost c : getCosts(km, pallets)) {
            if (c.getPrice() > outCost.getPrice()) {
                outCost = c;
            }
        }

        return outCost;
    }

    /// Save
    public Cost saveCost(Cost cost){
        return costRepository.save(cost);
    }

    public Cost saveCost(Long providerId, int km, int pallet, float price) throws Exception {
        Cost cost;
        List<Cost> costList= costRepository.findAllByProviderIdAndKmAndPallet(providerId, km, pallet);

        if (costList.size() == 0){
            cost = new Cost(providerId, km, pallet, price);
            cost.setProviderId(providerId);
            cost.setKm(km);
            cost.setPallet(pallet);
            cost.setPrice(price);
        } else if (costList.size() == 1) {
            cost = costList.get(0);
            cost.setPrice(price);
        } else {
            ShipsterLogger.logger.error("There are Multiple entries with ");
            throw new Exception("There are Multiple entries with ");
        }
        return saveCost(cost);
    }

    public Cost saveCost(Long costId, Long providerId, int km, int pallet, float price){
        Cost cost;

        if (costRepository.existsById(costId)){
            cost = costRepository.getById(costId);
            cost.setProviderId(providerId);
            cost.setKm(km);
            cost.setPallet(pallet);
            cost.setPrice(price);
        } else {
            cost = new Cost(providerId, km, pallet, price);
        }

        return saveCost(cost);
    }

}
