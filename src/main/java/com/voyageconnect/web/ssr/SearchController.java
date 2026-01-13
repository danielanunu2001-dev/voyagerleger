package com.voyageconnect.web.ssr;

import com.voyageconnect.voyage.Voyage;
import com.voyageconnect.voyage.VoyageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for displaying search results.
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    private final VoyageService voyageService;

    public SearchController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @GetMapping
    public String listVoyages(Model model) {
        List<Voyage> voyages = voyageService.findAll();
        model.addAttribute("voyages", voyages);
        return "search/list";
    }
}
