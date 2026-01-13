package com.voyageconnect.web.ssr;

import com.voyageconnect.common.exception.ResourceNotFoundException;
import com.voyageconnect.voyage.Voyage;
import com.voyageconnect.voyage.VoyageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to show the details of a specific voyage.
 */
@Controller
@RequestMapping("/voyage")
public class DetailsController {

    private final VoyageService voyageService;

    public DetailsController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @GetMapping("/{id}")
    public String voyageDetails(@PathVariable Long id, Model model) {
        Voyage voyage = voyageService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voyage", "id", id));
        model.addAttribute("voyage", voyage);
        return "search/details";
    }
}
