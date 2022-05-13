package ch.shipster.service;

import ch.shipster.data.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Timo

@Service
public class ProviderService {

    @Autowired
    ProviderRepository providerRepository;
}
