-- VITALSHARE DATABASE
-- FILE 1 OF 5: Create all seven tables and indexes.
-- Run this first in an EMPTY database named vitalshare_db.

CREATE TABLE users (
    user_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
        CHECK (role IN ('ADMIN', 'DONOR', 'REQUESTER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE blood_groups (
    blood_group_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    blood_group_name VARCHAR(3) NOT NULL UNIQUE
);

CREATE TABLE donors (
    donor_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(user_id),
    blood_group_id INTEGER NOT NULL REFERENCES blood_groups(blood_group_id),
    date_of_birth DATE NOT NULL CHECK (date_of_birth < CURRENT_DATE),
    phone VARCHAR(15) NOT NULL,
    city VARCHAR(60) NOT NULL,
    area VARCHAR(100) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    consent_to_contact BOOLEAN NOT NULL
);

CREATE TABLE blood_requests (
    request_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    requester_user_id INTEGER NOT NULL REFERENCES users(user_id),
    blood_group_id INTEGER NOT NULL REFERENCES blood_groups(blood_group_id),
    hospital_name VARCHAR(150) NOT NULL,
    city VARCHAR(60) NOT NULL,
    area VARCHAR(100) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    units_required INTEGER NOT NULL CHECK (units_required > 0),
    required_by TIMESTAMP NOT NULL,
    urgency VARCHAR(20) NOT NULL
        CHECK (urgency IN ('NORMAL', 'HIGH', 'CRITICAL')),
    status VARCHAR(30) DEFAULT 'OPEN'
        CHECK (status IN ('OPEN', 'PARTIALLY_FULFILLED', 'FULFILLED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donations (
    donation_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    donor_id INTEGER NOT NULL REFERENCES donors(donor_id),
    donation_date DATE NOT NULL CHECK (donation_date <= CURRENT_DATE),
    donation_type VARCHAR(20) NOT NULL
        CHECK (donation_type IN ('WHOLE_BLOOD', 'PLASMA', 'PLATELETS')),
    status VARCHAR(20) NOT NULL
        CHECK (status IN ('COMPLETED', 'CANCELLED'))
);

CREATE TABLE eligibility_rules (
    rule_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    donation_type VARCHAR(20) NOT NULL UNIQUE
        CHECK (donation_type IN ('WHOLE_BLOOD', 'PLASMA', 'PLATELETS')),
    minimum_interval_days INTEGER NOT NULL CHECK (minimum_interval_days > 0),
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE donor_matches (
    match_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    request_id INTEGER NOT NULL REFERENCES blood_requests(request_id),
    donor_id INTEGER NOT NULL REFERENCES donors(donor_id),
    match_status VARCHAR(20) NOT NULL DEFAULT 'SUGGESTED'
        CHECK (match_status IN ('SUGGESTED', 'CONTACTED', 'ACCEPTED', 'DECLINED', 'COMPLETED')),
    matched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (request_id, donor_id)
);

CREATE INDEX idx_donor_blood_group ON donors(blood_group_id);
CREATE INDEX idx_donor_location ON donors(pincode);
CREATE INDEX idx_request_urgency ON blood_requests(urgency);
CREATE INDEX idx_request_status ON blood_requests(status);
CREATE INDEX idx_donation_donor ON donations(donor_id);
CREATE INDEX idx_match_request ON donor_matches(request_id);
