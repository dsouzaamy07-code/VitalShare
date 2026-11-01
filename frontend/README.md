# VitalShare frontend

Plain HTML/CSS/JavaScript frontend for `Yogita_Backend/vitalshare`.

## Run
1. Start PostgreSQL and the Spring Boot backend on port 8080.
2. Open `index.html` using VS Code Live Server.
3. The API base defaults to `http://localhost:8080`. You can override it in the browser console with `localStorage.setItem('vs_api','http://localhost:8080')` before refreshing.

## Important backend limitations discovered
- There is **no login/authentication endpoint** in this backend. The registration page creates a user record but does not provide secure login.
- `POST /api/users` expects `fullName`, `email`, `passwordHash`, and `role`. The current backend saves the supplied value; do not use real passwords until the backend implements password hashing and authentication.
- Donor registration is a separate `POST /api/donors` call with `user: {userId}` and `bloodGroup: {bloodGroupId}`.
- No blood-group listing endpoint exists. `GROUP_IDS` in `index.html` assumes IDs 1–8 ordered A+, A-, B+, B-, AB+, AB-, O+, O-. **Verify the actual `blood_groups` table IDs before using these dropdowns.** Ideally add `GET /api/blood-groups`.
- Request creation requires a valid requester user ID. Donation creation requires an existing donor ID.
- The UI is a project prototype, not a clinical decision system. Confirm matches and eligibility with appropriate professionals.

## Endpoints used
`/api/users`, `/api/donors`, `/api/blood-requests/open`, `/api/blood-requests`, `/api/donor-matches/generate/{requestId}`, `/api/donations`, `/api/eligibility-rules/check/{donorId}/{donationType}`.
