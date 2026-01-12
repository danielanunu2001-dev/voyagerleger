-- =================================================================
-- VoyageConnect - Initial DDL and Seed Data
-- =================================================================

-- Drop tables if they exist to ensure a clean slate
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

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE destinations (
    id BIGSERIAL PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    description TEXT,
    images TEXT
);

CREATE TABLE voyages (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    start_date TIMESTAMPTZ NOT NULL,
    end_date TIMESTAMPTZ NOT NULL,
    seats_available INT NOT NULL,
    version BIGINT DEFAULT 0
);

CREATE TABLE hotels (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    rating INT,
    rooms_available INT
);

CREATE TABLE flights (
    id BIGSERIAL PRIMARY KEY,
    airline VARCHAR(255) NOT NULL,
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
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    amount NUMERIC(10, 2) NOT NULL
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    target_id BIGINT NOT NULL,
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


-- =================================================================
-- Seed Data
-- =================================================================

-- Roles
INSERT INTO roles (name) VALUES ('ROLE_CLIENT'), ('ROLE_ADMIN');

-- Users
-- Passwords are bcrypt encoded: 'password123' for client, 'adminpass' for admin
INSERT INTO users (username, email, password_hash) VALUES
('clientuser', 'client@test.com', '$2a$10$MebkhLreP/sK9EzPZYdBCOmk4.IzLO0bWDDfJZpS5JIyS68EfSrqy'),
('adminuser', 'admin@test.com', '$2a$10$b2eUtf.0Ab1jIBqH7VJQs.H29ZIh07TOX6R.MF15Fx8smdNf8xnxC');

-- User Roles
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 2);

-- Destinations
INSERT INTO destinations (country, city, description, images) VALUES
('France', 'Paris', 'La ville de l''amour et des lumières.', 'url1,url2'),
('Japon', 'Tokyo', 'Mélange fascinant de tradition et de modernité.', 'url3,url4'),
('Italie', 'Rome', 'Un musée à ciel ouvert.', 'url5,url6');

-- Voyages
INSERT INTO voyages (title, type, price, start_date, end_date, seats_available) VALUES
('Découverte de Paris', 'Circuit', 1200.00, '2026-06-01 00:00:00Z', '2026-06-07 00:00:00Z', 10),
('Aventure à Tokyo', 'Séjour', 2500.00, '2026-07-15 00:00:00Z', '2026-07-25 00:00:00Z', 5),
('Séjour à Rome', 'Séjour', 1500.00, '2026-08-10 00:00:00Z', '2026-08-17 00:00:00Z', 8),
('Tour de France', 'Circuit', 3000.00, '2026-09-01 00:00:00Z', '2026-09-15 00:00:00Z', 12);

-- Hotels
INSERT INTO hotels (name, city, rating, rooms_available) VALUES
('Hôtel de la Tour', 'Paris', 4, 20),
('Tokyo Grand Hotel', 'Tokyo', 5, 15);

-- Flights
INSERT INTO flights (airline, depart_at, arrive_at, seats_available) VALUES
('Air France', '2026-06-01 10:00:00Z', '2026-06-01 12:00:00Z', 50),
('Japan Airlines', '2026-07-15 08:00:00Z', '2026-07-15 22:00:00Z', 30);
