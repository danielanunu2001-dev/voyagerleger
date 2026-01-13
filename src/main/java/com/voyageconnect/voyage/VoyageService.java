package com.voyageconnect.voyage;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing voyages.
 */
public interface VoyageService {

    /**
     * Finds all available voyages.
     * @return a list of all voyages
     */
    List<Voyage> findAll();

    /**
     * Finds a voyage by its ID.
     * @param id the ID of the voyage
     * @return an Optional containing the voyage if found
     */
    Optional<Voyage> findById(Long id);

    // TODO: Add search methods with criteria (destination, dates, etc.)
}
