package com.agencevoyage.service;

import com.agencevoyage.entity.TravelPackage;
import com.agencevoyage.repository.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;

    @Autowired
    public TravelPackageService(TravelPackageRepository travelPackageRepository) {
        this.travelPackageRepository = travelPackageRepository;
    }

    public List<TravelPackage> findAll() {
        return travelPackageRepository.findAll();
    }
}
