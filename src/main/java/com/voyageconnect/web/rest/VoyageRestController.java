package com.voyageconnect.web.rest;

import com.voyageconnect.voyage.VoyageService;
import com.voyageconnect.web.rest.dto.VoyageDTO;
import com.voyageconnect.web.rest.mapper.VoyageMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voyages")
public class VoyageRestController {

    private final VoyageService voyageService;
    private final VoyageMapper voyageMapper;

    public VoyageRestController(VoyageService voyageService, VoyageMapper voyageMapper) {
        this.voyageService = voyageService;
        this.voyageMapper = voyageMapper;
    }

    @GetMapping
    public ResponseEntity<List<VoyageDTO>> getAllVoyages() {
        List<VoyageDTO> voyages = voyageMapper.toDto(voyageService.findAll());
        return ResponseEntity.ok(voyages);
    }
}
