-- VITALSHARE DATABASE
-- FILE 2 OF 5: Insert blood groups and all demo user accounts.
-- Run after FILE 1.

INSERT INTO blood_groups (blood_group_name) VALUES
('A+'), ('A-'), ('B+'), ('B-'),
('AB+'), ('AB-'), ('O+'), ('O-');

INSERT INTO users (full_name, email, password_hash, role) VALUES
-- Original users from the supplied database file
('Aarav Sharma', 'aarav@gmail.com', 'demo123', 'DONOR'),
('Riya Patel', 'riya@gmail.com', 'demo123', 'DONOR'),
('Rahul Mehta', 'rahul@gmail.com', 'demo123', 'DONOR'),
('Sneha Shah', 'sneha@gmail.com', 'demo123', 'DONOR'),
('Arjun Nair', 'arjun@gmail.com', 'demo123', 'DONOR'),
('Priya Desai', 'priya@gmail.com', 'demo123', 'REQUESTER'),
('Karan Joshi', 'karan@gmail.com', 'demo123', 'REQUESTER'),
('Neha Rao', 'neha@gmail.com', 'demo123', 'REQUESTER'),
('Ishaan Kapoor', 'ishaan.kapoor@demo.com', 'demo123', 'DONOR'),
('Ananya Iyer', 'ananya.iyer@demo.com', 'demo123', 'DONOR'),
('Vivaan Shah', 'vivaan.shah@demo.com', 'demo123', 'DONOR'),
('Diya Menon', 'diya.menon@demo.com', 'demo123', 'DONOR'),
('Kabir Deshmukh', 'kabir.deshmukh@demo.com', 'demo123', 'DONOR'),
('Meera Kulkarni', 'meera.kulkarni@demo.com', 'demo123', 'DONOR'),
('Aditya Rao', 'aditya.rao@demo.com', 'demo123', 'DONOR'),
('Sara Fernandes', 'sara.fernandes@demo.com', 'demo123', 'DONOR'),
('Reyansh Gupta', 'reyansh.gupta@demo.com', 'demo123', 'DONOR'),
('Tara Nair', 'tara.nair@demo.com', 'demo123', 'DONOR'),
('Nikhil Patil', 'nikhil.patil@demo.com', 'demo123', 'REQUESTER'),
('Pooja Shetty', 'pooja.shetty@demo.com', 'demo123', 'REQUESTER'),
('Rohan Verma', 'rohan.verma@demo.com', 'demo123', 'REQUESTER'),
('Aisha Khan', 'aisha.khan@demo.com', 'demo123', 'REQUESTER'),
('Dev Malhotra', 'dev.malhotra@demo.com', 'demo123', 'REQUESTER')
ON CONFLICT (email) DO NOTHING;

