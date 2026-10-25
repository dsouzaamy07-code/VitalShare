-- VITALSHARE DATABASE
-- FILE 05: ELIGIBILITY RULES, DONATIONS AND DONOR MATCHES
-- Prevents duplicate records when executed again.


-- STEP 1: INSERT ELIGIBILITY RULES

INSERT INTO eligibility_rules
(donation_type, minimum_interval_days, is_active)

VALUES
('WHOLE_BLOOD', 90, TRUE),
('PLASMA', 28, TRUE),
('PLATELETS', 14, TRUE)

ON CONFLICT (donation_type) DO NOTHING;


-- STEP 2: REMOVE ANY EXISTING DUPLICATE DONATIONS
-- Keeps the record with the smallest donation_id.

DELETE FROM donations d
USING (
    SELECT donation_id
    FROM (
        SELECT
            donation_id,
            ROW_NUMBER() OVER (
                PARTITION BY donor_id, donation_date, donation_type
                ORDER BY donation_id
            ) AS row_num
        FROM donations
    ) x
    WHERE row_num > 1
) duplicates
WHERE d.donation_id = duplicates.donation_id;


-- STEP 3: PREVENT FUTURE DUPLICATE DONATIONS

CREATE UNIQUE INDEX IF NOT EXISTS
uq_donations_donor_date_type
ON donations (donor_id, donation_date, donation_type);


-- STEP 4: INSERT DONATION RECORDS FOR 15 DONORS

INSERT INTO donations
(donor_id, donation_date, donation_type, status)

SELECT
    d.donor_id,
    v.donation_date::DATE,
    v.donation_type,
    v.status

FROM (VALUES

('aarav@gmail.com', '2026-05-10', 'WHOLE_BLOOD', 'COMPLETED'),
('riya@gmail.com', '2026-06-02', 'PLASMA', 'COMPLETED'),
('rahul@gmail.com', '2026-04-15', 'WHOLE_BLOOD', 'COMPLETED'),
('sneha@gmail.com', '2026-07-01', 'PLATELETS', 'COMPLETED'),
('arjun@gmail.com', '2026-03-20', 'WHOLE_BLOOD', 'COMPLETED'),

('ishaan.kapoor@demo.com', '2026-05-10', 'WHOLE_BLOOD', 'COMPLETED'),
('ananya.iyer@demo.com', '2026-06-02', 'PLASMA', 'COMPLETED'),
('vivaan.shah@demo.com', '2026-04-15', 'WHOLE_BLOOD', 'COMPLETED'),
('diya.menon@demo.com', '2026-07-01', 'PLATELETS', 'COMPLETED'),
('kabir.deshmukh@demo.com', '2026-03-20', 'WHOLE_BLOOD', 'COMPLETED'),
('meera.kulkarni@demo.com', '2026-06-18', 'PLASMA', 'COMPLETED'),
('aditya.rao@demo.com', '2026-05-25', 'WHOLE_BLOOD', 'CANCELLED'),
('sara.fernandes@demo.com', '2026-07-12', 'PLATELETS', 'COMPLETED'),
('reyansh.gupta@demo.com', '2026-02-11', 'WHOLE_BLOOD', 'COMPLETED'),
('tara.nair@demo.com', '2026-06-28', 'PLASMA', 'COMPLETED')

) AS v(email, donation_date, donation_type, status)

JOIN users u
ON u.email = v.email

JOIN donors d
ON d.user_id = u.user_id

ON CONFLICT (donor_id, donation_date, donation_type)
DO NOTHING;


-- STEP 5: INSERT DONOR MATCH SUGGESTIONS
-- Uses same blood group and PIN code for demo matching.
-- Existing matches are not inserted again.

INSERT INTO donor_matches
(request_id, donor_id, match_status)

SELECT
    br.request_id,
    d.donor_id,
    'SUGGESTED'

FROM blood_requests br

JOIN donors d
ON br.blood_group_id = d.blood_group_id
AND br.pincode = d.pincode

WHERE d.is_available = TRUE
AND d.consent_to_contact = TRUE

ON CONFLICT (request_id, donor_id) DO NOTHING;


-- STEP 6: DISPLAY ELIGIBILITY RULES

SELECT *
FROM eligibility_rules
ORDER BY rule_id;


-- STEP 7: DISPLAY DONATION HISTORY

SELECT
    dn.donation_id,
    u.full_name,
    dn.donation_date,
    dn.donation_type,
    dn.status

FROM donations dn

JOIN donors d
ON dn.donor_id = d.donor_id

JOIN users u
ON d.user_id = u.user_id

ORDER BY dn.donation_id;


-- STEP 8: DISPLAY DONOR MATCHES

SELECT
    dm.match_id,
    br.request_id,
    u.full_name AS donor_name,
    dm.match_status,
    dm.matched_at

FROM donor_matches dm

JOIN donors d
ON dm.donor_id = d.donor_id

JOIN users u
ON d.user_id = u.user_id

JOIN blood_requests br
ON dm.request_id = br.request_id

ORDER BY dm.match_id;


-- STEP 9: FINAL DATABASE RECORD COUNTS

SELECT 'users' AS table_name, COUNT(*) AS total FROM users

UNION ALL

SELECT 'blood_groups', COUNT(*) FROM blood_groups

UNION ALL

SELECT 'donors', COUNT(*) FROM donors

UNION ALL

SELECT 'blood_requests', COUNT(*) FROM blood_requests

UNION ALL

SELECT 'donations', COUNT(*) FROM donations

UNION ALL

SELECT 'eligibility_rules', COUNT(*) FROM eligibility_rules

UNION ALL

SELECT 'donor_matches', COUNT(*) FROM donor_matches

ORDER BY table_name;