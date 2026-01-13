-- =================================================================
-- VoyageConnect - Initial DDL and Seed Data
-- =================================================================

-- For tools like Flyway/Liquibase, table drops are usually handled automatically.
-- For a simple init.sql script, manual drops can be useful for re-running.

DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS reservations CASCADE;
DROP TABLE IF EXISTS voyages CASCADE;
DROP TABLE IF EXISTS flights CASCADE;
DROP TABLE IF EXISTS hotels CASCADE;
DROP TABLE IF EXISTS destinations CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;


-- =================================================================
-- Tables
-- =================================================================

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE destinations (
    id BIGSERIAL PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    description TEXT,
    images TEXT
);

CREATE TABLE voyages (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    destination_id BIGINT NOT NULL REFERENCES destinations(id),
    price NUMERIC(10, 2) NOT NULL,
    start_date TIMESTAMPTZ NOT NULL,
    end_date TIMESTAMPTZ NOT NULL,
    seats_available INT NOT NULL,
    version BIGINT DEFAULT 0
);

CREATE TABLE hotels (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    destination_id BIGINT NOT NULL REFERENCES destinations(id),
    rating INT,
    rooms_available INT
);

CREATE TABLE flights (
    id BIGSERIAL PRIMARY KEY,
    airline VARCHAR(100) NOT NULL,
    departure_destination_id BIGINT NOT NULL REFERENCES destinations(id),
    arrival_destination_id BIGINT NOT NULL REFERENCES destinations(id),
    depart_at TIMESTAMPTZ NOT NULL,
    arrive_at TIMESTAMPTZ NOT NULL,
    seats_available INT
);

CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    voyage_id BIGINT REFERENCES voyages(id),
    flight_id BIGINT REFERENCES flights(id),
    hotel_id BIGINT REFERENCES hotels(id),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    amount NUMERIC(10, 2) NOT NULL
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    target_id BIGINT NOT NULL,
    target_type VARCHAR(50) NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


-- =================================================================
-- Indexes
-- =================================================================

CREATE INDEX idx_destinations_city ON destinations(city);
CREATE INDEX idx_voyages_start_date ON voyages(start_date);
CREATE INDEX idx_reservations_user_id ON reservations(user_id);
CREATE INDEX idx_reviews_target ON reviews(target_id, target_type);


-- =================================================================
-- Seed Data
-- =================================================================

-- Roles
INSERT INTO roles (name) VALUES ('ROLE_CLIENT'), ('ROLE_ADMIN');

-- Users (Passwords are placeholders, to be replaced by CommandLineRunner or manual script)
-- password123 -> for client
-- adminpass -> for admin
INSERT INTO users (username, email, password_hash) VALUES
('client', 'client@voyageconnect.com', '$2a$10$9e8g3.yX/O5.L0.B/8z1u.bH8G9L9b6D5A/8s2z1e.k9a/C2c4e'),
('admin', 'admin@voyageconnect.com', '$2a$10$d1fG.gH6h.jK9l8m7n6o5.pQ4r3s2t1u0.vW9x8y7z.A6B5C4D3');

-- User Roles
INSERT INTO user_roles (user_id, role_id) VALUES
((SELECT id from users WHERE username = 'client'), (SELECT id from roles WHERE name = 'ROLE_CLIENT')),
((SELECT id from users WHERE username = 'admin'), (SELECT id from roles WHERE name = 'ROLE_ADMIN'));

-- Destinations
INSERT INTO destinations (country, city, description, images) VALUES
('France', 'Paris', 'Découvrez la ville lumière, ses musées et sa gastronomie.', 'paris_1.jpg,paris_2.jpg'),
('Japon', 'Tokyo', 'Une métropole vibrante où tradition et futurisme se rencontrent.', 'tokyo_1.jpg,tokyo_2.jpg'),
('Italie', 'Rome', 'Explorez les ruines antiques et les chefs-d''œuvre de la Renaissance.', 'rome_1.jpg,rome_2.jpg');

-- Voyages
INSERT INTO voyages (title, type, destination_id, price, start_date, end_date, seats_available) VALUES
('Semaine Romantique à Paris', 'Séjour', (SELECT id from destinations WHERE city = 'Paris'), 1200.00, '2026-06-01T00:00:00Z', '2026-06-08T00:00:00Z', 10),
('Aventure Culinaire à Tokyo', 'Circuit', (SELECT id from destinations WHERE city = 'Tokyo'), 2800.00, '2026-07-10T00:00:00Z', '2026-07-20T00:00:00Z', 8),
('Trésors de la Rome Antique', 'Circuit', (SELECT id from destinations WHERE city = 'Rome'), 1500.00, '2026-08-05T00:00:00Z', '2026-08-12T00:00:00Z', 12),
('Paris pour les Amoureux de l''Art', 'Séjour', (SELECT id from destinations WHERE city = 'Paris'), 1350.00, '2026-09-15T00:00:00Z', '2026-09-22T00:00:00Z', 6);

-- Hotels
INSERT INTO hotels (name, destination_id, rating, rooms_available) VALUES
('Hôtel Le Grand Parisien', (SELECT id from destinations WHERE city = 'Paris'), 4, 20),
('Tokyo Palace Hotel', (SELECT id from destinations WHERE city = 'Tokyo'), 5, 15);

-- Flights
INSERT INTO flights (airline, departure_destination_id, arrival_destination_id, depart_at, arrive_at, seats_available) VALUES
('Air France', (SELECT id from destinations WHERE city = 'Paris'), (SELECT id from destinations WHERE city = 'Rome'), '2026-08-05T10:00:00Z', '2026-08-05T12:00:00Z', 50),
('Japan Airlines', (SELECT id from destinations WHERE city = 'Paris'), (SELECT id from destinations WHERE city = 'Tokyo'), '2026-07-09T22:00:00Z', '2026-07-10T18:00:00Z', 30);
