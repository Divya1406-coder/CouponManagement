# Coupon System API

### Overview

The Coupon System API is a Spring Boot application that allows you to manage and apply discount coupons. Coupons can be categorized as:

 * Cart-wise: Discounts applied to the total cart value.

 * Product-wise: Discounts applied to specific products.
 
 * Buy X Get Y Free (BXGY): Discounts based on buying certain products to get others free.

 * The application uses H2, an in-memory database, for data storage.

### Features

 * Create Coupons: Add new discount coupons.

 * Retrieve Coupons: Fetch all or individual coupons.

 * Update Coupons: Modify existing coupon details.

 * Delete Coupons: Remove coupons from the system.

 * Applicable Coupons: Fetch coupons applicable to a given cart.

 * Apply Coupons: Apply a coupon to a cart and calculate the final price.

### Prerequisites

Java 11 or higher

Maven

IDE (e.g., STS, IntelliJ IDEA, Eclipse)

Getting Started

Clone the Repository

```bash
git clone https://github.com/Divya1406-coder/CouponManagement.git
cd CouponManagement
```

Build the Project

```bash
mvn clean install
```

Run the Application

```bash
mvn spring-boot:run
```

Access H2 Console

Visit http://localhost:8080/h2-console to view the in-memory database. Use the following credentials:

JDBC URL: (you can find it in logs of application by search these "HikariPool-1 - Added connection conn0: url="

Username: kanniraj

Password: kanniraj

Endpoints

Base URL: /coupons

Create a Coupon

POST /coupons

Request Body:

```bash
{
  "type": "cart-wise",
  "details": {
    "threshold": 100,
    "discount": 10
  }
}
```
Response:

```bash
{
  "id": 1,
  "type": "cart-wise",
  "details": {
    "threshold": 100,
    "discount": 10
  }
}
```
2. Retrieve All Coupons

GET /coupons

Response:

```bash
[
  {
    "id": 1,
    "type": "cart-wise",
    "details": {
      "threshold": 100,
      "discount": 10
    }
  }
]
```

Retrieve a Coupon

GET /coupons/{id}

Response:

```bash
{
  "id": 1,
  "type": "cart-wise",
  "details": {
    "threshold": 100,
    "discount": 10
  }
}
```

Update a Coupon

PUT /coupons/{id}

Request Body:

```bash
{
  "type": "product-wise",
  "details": {
    "product_id": 101,
    "discount": 20
  }
}
```
Response:

```bash
{
  "id": 1,
  "type": "product-wise",
  "details": {
    "product_id": 101,
    "discount": 20
  }
}
```

Delete a Coupon

DELETE /coupons/{id}

Response:

"Coupon deleted successfully."

Get Applicable Coupons

POST /coupons/applicable-coupons

Request Body:

```bash
{
  "items": [
    {
      "productId": 101,
      "quantity": 2,
      "price": 50
    }
  ],
  "totalPrice": 100
}
```

Response:

```bash
[
  {
    "couponId": 1,
    "type": "cart-wise",
    "discount": 10
  }
]
```

Apply a Coupon

POST /coupons/apply-coupon/{id}

Request Body:

```bash
{
  "items": [
    {
      "productId": 101,
      "quantity": 2,
      "price": 50
    }
  ],
  "totalPrice": 100
}
```

Response:

```bash
{
  "items": [
    {
      "productId": 101,
      "quantity": 2,
      "price": 50
    }
  ],
  "totalPrice": 100,
  "totalDiscount": 10,
  "finalPrice": 90
}
```

### Notes

 * H2 database resets with each application restart.

 * Extendable for other database implementations (e.g., MySQL, PostgreSQL).

### Future Enhancements

 * Add authentication and authorization.

 * Support for scheduled coupon expirations.

 * Enhanced reporting for applied coupons.
