-- VITALSHARE DATABASE
-- FILE 04: ADDITIONAL REQUESTERS AND BLOOD REQUESTS

-- STEP 1: Add 5 additional requester accounts

INSERT INTO users (full_name, email, password_hash, role)
VALUES
('Nikhil Patil', 'nikhil.patil@demo.com', 'demo123', 'REQUESTER'),
('Pooja Shetty', 'pooja.shetty@demo.com', 'demo123', 'REQUESTER'),
('Rohan Verma', 'rohan.verma@demo.com', 'demo123', 'REQUESTER'),
('Aisha Khan', 'aisha.khan@demo.com', 'demo123', 'REQUESTER'),
('Dev Malhotra', 'dev.malhotra@demo.com', 'demo123', 'REQUESTER')
ON CONFLICT (email) DO NOTHING;


-- STEP 2: Add 10 additional blood requests

INSERT INTO blood_requests
(requester_user_id, blood_group_id, hospital_name, city,
 area, pincode, units_required, required_by, urgency, status)

SELECT
    u.user_id,
    bg.blood_group_id,
    v.hospital_name,
    'Mumbai',
    v.area,
    v.pincode,
    v.units_required,
    v.required_by::TIMESTAMP,
    v.urgency,
    'OPEN'

FROM (VALUES

('nikhil.patil@demo.com', 'A+', 'Lilavati Hospital',
 'Bandra', '400050', 2, '2027-02-02 10:00:00', 'CRITICAL'),

('pooja.shetty@demo.com', 'O+', 'Kokilaben Hospital',
 'Andheri', '400053', 1, '2027-02-04 12:00:00', 'HIGH'),

('rohan.verma@demo.com', 'B+', 'Hinduja Hospital',
 'Dadar', '400014', 2, '2027-02-06 09:00:00', 'NORMAL'),

('aisha.khan@demo.com', 'AB+', 'Nanavati Hospital',
 'Borivali', '400092', 1, '2027-02-08 14:00:00', 'HIGH'),

('dev.malhotra@demo.com', 'O-', 'Fortis Hospital',
 'Kurla', '400070', 1, '2027-02-10 11:00:00', 'CRITICAL'),

('nikhil.patil@demo.com', 'A-', 'Breach Candy Hospital',
 'Bandra', '400050', 1, '2027-02-12 10:00:00', 'NORMAL'),

('pooja.shetty@demo.com', 'B-', 'SevenHills Hospital',
 'Andheri', '400053', 2, '2027-02-14 16:00:00', 'HIGH'),

('rohan.verma@demo.com', 'AB-', 'Jaslok Hospital',
 'Dadar', '400014', 1, '2027-02-16 08:00:00', 'NORMAL'),

('aisha.khan@demo.com', 'A+', 'Holy Family Hospital',
 'Bandra', '400050', 1, '2027-02-18 13:00:00', 'HIGH'),

('dev.malhotra@demo.com', 'O+', 'ESIC Hospital',
 'Andheri', '400053', 1, '2027-02-20 15:00:00', 'NORMAL')

) AS v(email, blood_group, hospital_name, area, pincode,
       units_required, required_by, urgency)

JOIN users u
ON u.email = v.email

JOIN blood_groups bg
ON bg.blood_group_name = v.blood_group

WHERE NOT EXISTS (
    SELECT 1
    FROM blood_requests br
    WHERE br.requester_user_id = u.user_id
      AND br.hospital_name = v.hospital_name
      AND br.required_by = v.required_by::TIMESTAMP
);


-- STEP 3: Display all blood requests

SELECT *
FROM blood_requests
ORDER BY request_id;

SELECT
    requester_user_id,
    blood_group_id,
    hospital_name,
    area,
    pincode,
    units_required,
    required_by,
    COUNT(*) AS duplicate_count
FROM blood_requests
GROUP BY
    requester_user_id,
    blood_group_id,
    hospital_name,
    area,
    pincode,
    units_required,
    required_by
HAVING COUNT(*) > 1;

BEGIN;

-- Identify duplicate requests and their original request IDs

CREATE TEMP TABLE duplicate_request_map
ON COMMIT DROP AS
SELECT request_id AS duplicate_id, keeper_id
FROM (
    SELECT
        request_id,
        FIRST_VALUE(request_id) OVER (
            PARTITION BY
                requester_user_id,
                blood_group_id,
                hospital_name,
                city,
                area,
                pincode,
                units_required,
                required_by,
                urgency,
                status
            ORDER BY request_id
        ) AS keeper_id,
        ROW_NUMBER() OVER (
            PARTITION BY
                requester_user_id,
                blood_group_id,
                hospital_name,
                city,
                area,
                pincode,
                units_required,
                required_by,
                urgency,
                status
            ORDER BY request_id
        ) AS row_num
    FROM blood_requests
) x
WHERE row_num > 1;


-- Preserve donor matches under the original request IDs

INSERT INTO donor_matches
(request_id, donor_id, match_status, matched_at)

SELECT
    m.keeper_id,
    dm.donor_id,
    dm.match_status,
    dm.matched_at
FROM donor_matches dm
JOIN duplicate_request_map m
ON dm.request_id = m.duplicate_id

ON CONFLICT (request_id, donor_id) DO NOTHING;


-- Remove matches linked to duplicate requests

DELETE FROM donor_matches dm
USING duplicate_request_map m
WHERE dm.request_id = m.duplicate_id;


-- Delete only duplicate blood requests

DELETE FROM blood_requests br
USING duplicate_request_map m
WHERE br.request_id = m.duplicate_id;

COMMIT;

SELECT COUNT(*) AS total_requests
FROM blood_requests;