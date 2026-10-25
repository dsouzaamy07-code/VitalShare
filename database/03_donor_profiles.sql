-- VITALSHARE DATABASE
-- FILE 03: ALL USERS AND DONOR PROFILES
-- Original 5 donors + 10 additional donors

-- STEP 1: Add the 10 additional donor accounts
-- ON CONFLICT prevents duplicate email accounts.

INSERT INTO users (full_name, email, password_hash, role)
VALUES
('Ishaan Kapoor', 'ishaan.kapoor@demo.com', 'demo123', 'DONOR'),
('Ananya Iyer', 'ananya.iyer@demo.com', 'demo123', 'DONOR'),
('Vivaan Shah', 'vivaan.shah@demo.com', 'demo123', 'DONOR'),
('Diya Menon', 'diya.menon@demo.com', 'demo123', 'DONOR'),
('Kabir Deshmukh', 'kabir.deshmukh@demo.com', 'demo123', 'DONOR'),
('Meera Kulkarni', 'meera.kulkarni@demo.com', 'demo123', 'DONOR'),
('Aditya Rao', 'aditya.rao@demo.com', 'demo123', 'DONOR'),
('Sara Fernandes', 'sara.fernandes@demo.com', 'demo123', 'DONOR'),
('Reyansh Gupta', 'reyansh.gupta@demo.com', 'demo123', 'DONOR'),
('Tara Nair', 'tara.nair@demo.com', 'demo123', 'DONOR')
ON CONFLICT (email) DO NOTHING;


-- STEP 2: Insert all 15 donor profiles

INSERT INTO donors
(user_id, blood_group_id, date_of_birth, phone,
 city, area, pincode, is_available, consent_to_contact)

SELECT
    u.user_id,
    bg.blood_group_id,
    v.date_of_birth::DATE,
    v.phone,
    'Mumbai',
    v.area,
    v.pincode,
    v.is_available,
    TRUE

FROM (VALUES

-- Original 5 donors

('aarav@gmail.com', 'A+', '2003-05-12',
 '9876543210', 'Bandra', '400050', TRUE),

('riya@gmail.com', 'B+', '2002-08-20',
 '9876543211', 'Andheri', '400053', TRUE),

('rahul@gmail.com', 'AB+', '2004-01-15',
 '9876543212', 'Dadar', '400014', TRUE),

('sneha@gmail.com', 'O+', '2003-11-10',
 '9876543213', 'Borivali', '400092', TRUE),

('arjun@gmail.com', 'A-', '2002-04-25',
 '9876543214', 'Kurla', '400070', FALSE),

-- 10 additional donors

('ishaan.kapoor@demo.com', 'A+', '2001-02-14',
 '9000000001', 'Bandra', '400050', TRUE),

('ananya.iyer@demo.com', 'O+', '2002-06-21',
 '9000000002', 'Andheri', '400053', TRUE),

('vivaan.shah@demo.com', 'B+', '2000-09-09',
 '9000000003', 'Dadar', '400014', TRUE),

('diya.menon@demo.com', 'AB+', '2001-12-03',
 '9000000004', 'Borivali', '400092', TRUE),

('kabir.deshmukh@demo.com', 'O-', '2002-03-17',
 '9000000005', 'Kurla', '400070', TRUE),

('meera.kulkarni@demo.com', 'A-', '2000-07-25',
 '9000000006', 'Bandra', '400050', TRUE),

('aditya.rao@demo.com', 'B-', '2001-10-11',
 '9000000007', 'Andheri', '400053', FALSE),

('sara.fernandes@demo.com', 'AB-', '2002-01-29',
 '9000000008', 'Dadar', '400014', TRUE),

('reyansh.gupta@demo.com', 'A+', '2000-04-06',
 '9000000009', 'Borivali', '400092', TRUE),

('tara.nair@demo.com', 'O+', '2001-08-19',
 '9000000010', 'Kurla', '400070', TRUE)

) AS v(email, blood_group, date_of_birth,
       phone, area, pincode, is_available)

JOIN users u
ON u.email = v.email

JOIN blood_groups bg
ON bg.blood_group_name = v.blood_group

WHERE NOT EXISTS (
    SELECT 1
    FROM donors d
    WHERE d.user_id = u.user_id
);


-- STEP 3: Display all donor records

SELECT
    d.donor_id,
    u.full_name,
    u.email,
    bg.blood_group_name,
    d.phone,
    d.city,
    d.area,
    d.pincode,
    d.is_available,
    d.consent_to_contact

FROM donors d

JOIN users u
ON d.user_id = u.user_id

JOIN blood_groups bg
ON d.blood_group_id = bg.blood_group_id

ORDER BY d.donor_id;