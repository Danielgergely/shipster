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

    public List<Cost> getCosts(Float km, int pallets) {
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

    public Cost getCost(Long providerId, Float km, int pallets) {
        for (Cost c : costRepository.findByProviderIdAndPalletOrderByKmDesc(providerId, pallets)) {
            if (c.getKm() > km) {
                return c;
            }
        }
        return null;
    }

    public Cost getCheapestCost(Float km, int pallets) {
        List<Cost> inCost = getCosts(km, pallets);
        Cost outCost = inCost.get(0);

        for (Cost c : getCosts(km, pallets)) {
            if (c.getPrice() < outCost.getPrice()) {
                outCost = c;
            }
        }

        return outCost;
    }

}
