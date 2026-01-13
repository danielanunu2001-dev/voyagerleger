# VoyageConnect

VoyageConnect est une application web complète d'agence de voyage développée avec Java, Spring Boot, et une architecture hybride (SSR avec JSP et API REST pour AJAX).

## Table des matières

1.  [Contexte](#contexte)
2.  [Stack Technique](#stack-technique)
3.  [Prérequis](#prérequis)
4.  [Démarrage Rapide (Docker)](#démarrage-rapide-docker)
5.  [Développement Local (Sans Docker)](#développement-local-sans-docker)
6.  [Exécuter les Tests](#exécuter-les-tests)
7.  [Variables d'Environnement](#variables-denvironnement)
8.  [Architecture](#architecture)
9.  [Intégration Continue (CI)](#intégration-continue-ci)
10. [Intégration de Paiement (Stripe)](#intégration-de-paiement-stripe)

## Contexte

Ce projet simule une plateforme de réservation de voyages, permettant aux utilisateurs de rechercher des offres, de s'inscrire, de se connecter et de faire des réservations. Il inclut une interface d'administration basique et est conçu pour être sécurisé, maintenable et scalable.

## Stack Technique

*   **Backend**: Java 21, Spring Boot 3.x, Spring Security 6+, Spring Data JPA (Hibernate)
*   **Frontend**: JSP, JSTL, HTML5, CSS3, Bootstrap 5, JavaScript (jQuery pour AJAX)
*   **Base de données**: PostgreSQL (production/Docker), H2 (profil `local`)
*   **Tests**: JUnit 5, Mockito, Testcontainers
*   **Conteneurisation**: Docker & Docker Compose
*   **CI**: GitHub Actions
*   **Build**: Maven

## Prérequis

*   JDK 21
*   Maven 3.8+
*   Docker et Docker Compose (pour l'environnement conteneurisé)
*   Une instance de PostgreSQL (pour le développement local sans Docker)

## Démarrage Rapide (Docker)

C'est la méthode recommandée pour lancer l'application localement.

1.  **Configurer les variables d'environnement**:
    Créez un fichier `.env` à la racine du projet en vous basant sur `.env.example` (à créer) ou configurez directement les variables dans `docker-compose.yml`.

    ```
    ADMIN_PASSWORD=yoursecureadminpassword
    CLIENT_PASSWORD=yoursecureclientpassword
    ```

2.  **Construire et démarrer les conteneurs**:
    ```bash
    docker-compose up --build
    ```

3.  **Accéder à l'application**:
    *   Application: [http://localhost:8080](http://localhost:8080)
    *   Adminer (gestionnaire de BDD): [http://localhost:8081](http://localhost:8081)

    Les identifiants de la base de données pour Adminer sont `user` / `password` et le nom de la base `voyageconnect`.

## Développement Local (Sans Docker)

1.  **Configurer PostgreSQL**:
    Assurez-vous d'avoir une base de données PostgreSQL en cours d'exécution. Créez une base de données et un utilisateur.

2.  **Configurer les variables d'environnement**:
    Exportez les variables suivantes dans votre terminal :
    ```bash
    export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/votrenomdebd
    export SPRING_DATASOURCE_USERNAME=votreuser
    export SPRING_DATASOURCE_PASSWORD=votrepassword
    export ADMIN_PASSWORD=yoursecureadminpassword
    export CLIENT_PASSWORD=yoursecureclientpassword
    ```

3.  **Lancer l'application avec le profil `local` (H2)**:
    Cette option ne nécessite pas PostgreSQL.
    ```bash
    mvn spring-boot:run -Plocal
    ```

4.  **Lancer l'application avec PostgreSQL**:
    Assurez-vous que les variables d'environnement sont configurées.
    ```bash
    mvn spring-boot:run
    ```

## Exécuter les Tests

*   **Exécuter les tests unitaires uniquement**:
    ```bash
    mvn test
    ```

*   **Exécuter tous les tests (unitaires + intégration)**:
    Cela nécessite que Docker soit en cours d'exécution car les tests d'intégration utilisent Testcontainers.
    ```bash
    mvn verify
    ```

## Variables d'Environnement

| Variable                     | Description                                                              | Défaut dans `docker-compose` |
| ---------------------------- | ------------------------------------------------------------------------ | --------------------------- |
| `SPRING_DATASOURCE_URL`      | URL JDBC pour la connexion à la base de données.                         | `jdbc:postgresql://db:5432/voyageconnect` |
| `SPRING_DATASOURCE_USERNAME` | Nom d'utilisateur pour la base de données.                               | `user` |
| `SPRING_DATASOURCE_PASSWORD` | Mot de passe pour la base de données.                                    | `password` |
| `ADMIN_PASSWORD`             | Mot de passe initial pour l'utilisateur `admin`.                         | `adminpass` |
| `CLIENT_PASSWORD`            | Mot de passe initial pour l'utilisateur `client`.                        | `password123` |
| `STRIPE_SECRET_KEY`          | Clé secrète Stripe pour l'intégration des paiements.                     | `ENV_PLACEHOLDER` |
| `STRIPE_WEBHOOK_SECRET`      | Secret pour vérifier les signatures des webhooks Stripe.                 | `ENV_PLACEHOLDER` |
| `PAYMENT_PROVIDER`           | Active un fournisseur de paiement (`stripe` ou `stub`).                  | `stub` |

## Architecture

Le projet suit une architecture MVC classique enrichie de services et de repositories, avec une structure de packages basée sur les fonctionnalités.

*   `com.voyageconnect.config`: Configuration de Spring (Sécurité, MVC, etc.).
*   `com.voyageconnect.user`, `...booking`, etc.: Packages par fonctionnalité contenant les entités, repositories, services.
*   `com.voyageconnect.web.ssr`: Contrôleurs Spring MVC (`@Controller`) pour les pages rendues côté serveur.
*   `com.voyageconnect.web.rest`: Contrôleurs Spring (`@RestController`) pour l'API REST consommée par AJAX.
*   `src/main/webapp/WEB-INF/jsp`: Vues JSP.
*   `src/main/resources/static`: Fichiers statiques (CSS, JS, images).

## Intégration Continue (CI)

Le workflow `ci.yml` dans `.github/workflows` est déclenché à chaque `push` ou `pull_request` sur la branche `main`. Il effectue les étapes suivantes :
1.  Démarre un conteneur PostgreSQL pour les tests d'intégration.
2.  Configure Java 21.
3.  Compile le projet et exécute la commande `mvn verify`, qui lance les tests unitaires et d'intégration.
4.  Vérifie que la couverture de code (JaCoCo) est supérieure à 60%.
5.  Télécharge le rapport de couverture de code en tant qu'artefact.

## Intégration de Paiement (Stripe)

L'intégration de Stripe est désactivée par défaut (`payment.provider=stub`). Pour l'activer :

1.  **Configurer les variables d'environnement**:
    ```
    export PAYMENT_PROVIDER=stripe
    export STRIPE_SECRET_KEY=sk_test_...
    export STRIPE_WEBHOOK_SECRET=whsec_...
    ```
2.  Relancez l'application. Le `StripePaymentService` sera alors activé.

**Webhook Idempotence**: L'application (dans la Phase 10) garantit que les webhooks de Stripe ne sont traités qu'une seule fois en stockant les IDs d'événement dans une table `stripe_events`.
