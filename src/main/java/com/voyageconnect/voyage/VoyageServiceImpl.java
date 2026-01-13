package com.voyageconnect.voyage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the VoyageService.
 */
@Service
public class VoyageServiceImpl implements VoyageService {

    private final VoyageRepository voyageRepository;

    public VoyageServiceImpl(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Voyage> findAll() {
        return voyageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Voyage> findById(Long id) {
        return voyageRepository.findById(id);
    }
}
