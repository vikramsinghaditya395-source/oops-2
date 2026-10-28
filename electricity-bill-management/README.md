# VoltPay - Electricity Bill Management (Java OOP)

## Requirements
- JDK 17+
- Maven 3.9+
- MySQL 8+

## Run
1. Create a MySQL user/password or set environment variables `DB_USER` and `DB_PASSWORD`.
2. The configured JDBC URL creates the `voltpay` database automatically if the MySQL user has permission.
3. From this directory run: `mvn spring-boot:run`
4. Open http://localhost:8080

## Demo workflow
1. Register a customer from the page.
2. Create a tariff (required before creating a bill) using an API client:
   `PUT http://localhost:8080/api/tariffs/Residential`
   JSON body: `{"rate":6.5,"fixedCharge":100}`
   Also create `Commercial` with `{"rate":9.5,"fixedCharge":250}`.
3. Create a bill from the page. Units = current reading - previous reading; amount = units * tariff rate + fixed charge.
4. Use the bill list's Mark paid action to simulate a payment.

## REST endpoints
- `GET/POST /api/customers`
- `GET /api/customers/{customerCode}`
- `POST /api/customers/demo-login` (demo lookup only; not secure authentication)
- `GET /api/bills`
- `GET /api/bills/customer/{customerCode}`
- `POST /api/bills`
- `PATCH /api/bills/{id}/pay` (simulated payment status only)
- `GET /api/tariffs`
- `PUT /api/tariffs/{category}`

## OOP and error-handling notes
- Encapsulation: entities use private fields and controlled methods.
- Inheritance: not forced where it does not improve the model.
- Polymorphism: can be demonstrated by adding different billing strategies when needed.
- Exception handling: `ApiExceptionHandler` centralizes API error responses, including validation and not-found errors.

## Important security notes
This is a learning/demo scaffold, not a production-ready billing service. It intentionally does not include real OTP/SMS, admin authentication, authorization, CSRF policy, rate limiting, or real payment gateway integration. Add Spring Security, hashed credentials, secure OTP delivery, role-based authorization, and payment-provider verification before deployment. Never expose customer data or mark payments paid based only on an unauthenticated request.
