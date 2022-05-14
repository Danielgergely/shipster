package ch.shipster.service;

import ch.shipster.data.domain.Provider;
import ch.shipster.data.repository.ProviderRepository;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Timo

@Service
public class ProviderService {

    @Autowired
    ProviderRepository providerRepository;

    public Provider createProvider(String name) throws DuplicateMemberException {
        return saveProvider(new Provider(name));
    }

    public Provider saveProvider(String name) throws DuplicateMemberException {
        Optional<Provider> sameName = getProviderByName(name);

        if (sameName.isPresent()){
            return sameName.get();
        } else {
            return saveProvider(new Provider(name));
        }
    }

    public Provider saveProvider(Provider provider) throws DuplicateMemberException {
        Optional<Provider> sameName = getProviderByName(provider.getName());
        if (sameName.isPresent()){
            if (provider.getId().equals(null)){
                return sameName.get();
            } else if (sameName.get().getId().equals(provider.getId())){
                return providerRepository.save(provider);
            } else {
                throw new DuplicateMemberException("Provider with name "+provider.getName()+"already exists under Id " + sameName.get().getId());
            }
        } else {
            if (provider.getId().equals(null)) {
                return providerRepository.save(new Provider(provider.getName()));
            } else {
                return providerRepository.save(provider);
            }
        }
    }

    public Optional<Provider> getProviderByName(String name) throws DuplicateMemberException {
        List<Provider> providerList = providerRepository.getAllByName(name);
        if (providerList.size() == 0) {
            return Optional.empty();
        } else if (providerList.size() == 1){
            return Optional.ofNullable(providerList.get(0));
        } else  {
            throw new DuplicateMemberException("Multiple Provider under the Name" + name);
        }
    }
}
