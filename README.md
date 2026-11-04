# VitalShare


# VitalShare – Lifesaving Resources Shared Instantly

## Project Overview
VitalShare is a Java and PostgreSQL-based blood donation
management application that helps connect blood requests
with potential donors.

## Technologies Used
- Java 25
- Spring Boot
- PostgreSQL
- HTML, CSS and JavaScript
- Maven
- Postman

## Main Features
1. User Registration and Login
2. User Profile Management
3. Donor Directory and Search
4. Blood Request Management
5. Automated Donor Suggestions
6. Donation Records
7. Donor Eligibility Rule Check

## How to Run

### Backend
1. Open the backend project in IntelliJ IDEA.
2. Configure PostgreSQL connection details.
3. Make sure the database `vitalshare_db` exists.
4. Run the Spring Boot application.

### Frontend
1. Open the frontend folder in VS Code.
2. Open `index.html` using Live Server.
3. Make sure the backend is running on port 8080.

## Database
Database: vitalshare_db

The database contains seven main tables:
- users
- blood_groups
- donors
- blood_requests
- donations
- eligibility_rules
- donor_matches

## Important Note
Donor suggestions and eligibility calculations are
application features for demonstration purposes.
They do not replace professional medical screening
or establish clinical compatibility.
