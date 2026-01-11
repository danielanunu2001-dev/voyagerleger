package com.agencevoyage;

import com.agencevoyage.entity.TravelPackage;
import com.agencevoyage.repository.TravelPackageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(TravelPackageRepository repository) {
        return args -> {
            repository.saveAll(List.of(
                new TravelPackage("Aventure à Bali", "Bali, Indonésie", "Explorez les temples anciens, les rizières verdoyantes et les plages de sable noir de Bali.", new BigDecimal("1250.00"), "https://images.unsplash.com/photo-1537996194471-e657df97525d?w=500&q=80"),
                new TravelPackage("Escapade à Rome", "Rome, Italie", "Découvrez les trésors historiques de la Ville Éternelle, du Colisée au Vatican.", new BigDecimal("980.50"), "https://images.unsplash.com/photo-1529260830199-42c24129f196?w=500&q=80"),
                new TravelPackage("Découverte de Kyoto", "Kyoto, Japon", "Immergez-vous dans la culture japonaise traditionnelle avec ses temples, ses jardins zen et ses geishas.", new BigDecimal("1800.00"), "https://images.unsplash.com/photo-1528181304800-259b08848526?w=500&q=80"),
                new TravelPackage("Safari en Tanzanie", "Tanzanie, Afrique", "Vivez l'expérience d'un safari inoubliable au cœur du Serengeti et admirez la faune sauvage.", new BigDecimal("3200.75"), "https://images.unsplash.com/photo-1534430480872-7013fa2a3f9e?w=500&q=80")
            ));
        };
    }
}
