package com.voyageconnect.web.rest.mapper;

import com.voyageconnect.voyage.Voyage;
import com.voyageconnect.web.rest.dto.VoyageDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoyageMapper {

    public VoyageDTO toDto(Voyage voyage) {
        if (voyage == null) {
            return null;
        }

        VoyageDTO dto = new VoyageDTO();
        dto.setId(voyage.getId());
        dto.setTitle(voyage.getTitle());
        dto.setType(voyage.getType());
        dto.setPrice(voyage.getPrice());
        dto.setStartDate(voyage.getStartDate());
        dto.setEndDate(voyage.getEndDate());
        dto.setSeatsAvailable(voyage.getSeatsAvailable());
        if (voyage.getDestination() != null) {
            dto.setDestinationCity(voyage.getDestination().getCity());
            dto.setDestinationCountry(voyage.getDestination().getCountry());
        }

        return dto;
    }

    public List<VoyageDTO> toDto(List<Voyage> voyages) {
        return voyages.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
