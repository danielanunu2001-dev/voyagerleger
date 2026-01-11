# Agence de Voyage - Application Web

Cette application est une plateforme web complète pour une agence de voyage, conçue pour permettre aux utilisateurs de rechercher, consulter et réserver des voyages.

## Stack Technique

L'application est construite sur une architecture hybride moderne et robuste, alliant la puissance de Spring Boot à la flexibilité des technologies frontend classiques.

### Backend
- **Framework Principal**: Spring Boot 3.x
- **Langage**: Java 17
- **Accès aux données**: Spring Data JPA / Hibernate
- **Sécurité**: Spring Security
- **Serveur d'application**: Tomcat embarqué

### Frontend
- **Templates**: JavaServer Pages (JSP)
- **Styling**: Bootstrap 5, CSS3
- **Interactivité**: JavaScript, jQuery pour les appels AJAX

### Base de données
- **Développement**: H2 (base de données en mémoire)
- **Production (cible)**: PostgreSQL

### Outil de build
- **Gestion de projet**: Apache Maven

## Déploiement et Lancement

Suivez ces étapes pour compiler et exécuter l'application sur votre machine locale.

### Prérequis
- Java Development Kit (JDK) 17 ou supérieur
- Apache Maven 3.6 ou supérieur

### Compilation
Pour compiler le projet et télécharger toutes les dépendances, exécutez la commande suivante à la racine du projet :

```bash
mvn clean install
```
Cette commande va générer un fichier `.war` exécutable dans le répertoire `target/`.

### Exécution
Une fois la compilation terminée, vous pouvez lancer l'application avec la commande suivante :

```bash
java -jar target/agence-voyage-app-0.0.1-SNAPSHOT.war
```
L'application démarrera et sera accessible par défaut à l'adresse suivante : [http://localhost:8080](http://localhost:8080).
