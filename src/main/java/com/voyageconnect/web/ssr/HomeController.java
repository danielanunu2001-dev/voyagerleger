package com.voyageconnect.web.ssr;

import com.voyageconnect.voyage.Voyage;
import com.voyageconnect.voyage.VoyageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {

    private final VoyageService voyageService;

    public HomeController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // For the demo, we show a few voyages on the home page.
        // A more advanced version might show "featured" or "popular" voyages.
        List<Voyage> voyages = voyageService.findAll();
        model.addAttribute("voyages", voyages);
        return "index";
    }
}
