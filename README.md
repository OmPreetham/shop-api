# E-Commerce API Application

A modern RESTful API for e-commerce applications built with Spring Boot. This application provides a complete backend solution for managing products, shopping carts, and orders.

## 📚 Table of Contents

- [Technology Stack](#technology-stack)
- [Key Features](#key-features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Contributing](#contributing)

## 🛠️ Technology Stack

- **Java 17+**
- **Spring Boot**: Core framework
- **Spring Data JPA**: Data access and ORM
- **Spring MVC**: RESTful API development
- **Jakarta Bean Validation**: Input validation
- **MySQL**: Database (can be easily changed to other databases)
- **Maven**: Dependency management and build tool
- **JUnit & Mockito**: Testing (implied)

## ✨ Key Features

### Product Management

- CRUD operations for products
- Search products by name
- Filter products by category
- Pagination and sorting
- Stock management

### Shopping Cart

- Add products to cart
- Update product quantities
- Remove products from cart
- View cart with calculated totals
- Per-user cart management

### Order Processing

- Place orders from cart contents
- Track order status (PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED)
- Update order status
- Payment status tracking
- Order history for users
- Detailed order information

### Other Features

- Clean 3-layer architecture (Controller, Service, Repository)
- Robust data validation
- Transaction management
- Entity relationship management
- DTOs for API contracts
- Mapper pattern implementation

## 📂 Project Structure

The application follows a clean architecture pattern with separation of concerns:

```
src/main/java/com/ompreetham/
├── controller/          # REST API endpoints
│   ├── ProductController.java
│   ├── CartController.java
│   └── OrderController.java
├── service/             # Business logic
│   ├── ProductService.java
│   ├── CartService.java
│   └── OrderService.java
├── repository/          # Data access
│   ├── ProductRepository.java
│   ├── CartRepository.java
│   └── OrderRepository.java
├── entity/              # Database models
│   ├── Product.java
│   ├── Category.java
│   ├── User.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── OrderStatus.java
│   └── PaymentStatus.java
├── dto/                 # Data Transfer Objects
│   ├── ProductDTO.java
│   ├── CartDTO.java
│   ├── OrderDTO.java
│   └── ... (request/response DTOs)
├── mapper/              # Entity-DTO converters
│   ├── ProductMapper.java
│   ├── CartMapper.java
│   └── OrderMapper.java
└── exception/           # Custom exceptions
    └── ResourceNotFoundException.java
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- MySQL (or another compatible database)

### Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/e-commerce-api.git
   cd e-commerce-api
   ```

2. **Configure the database**

   Edit `src/main/resources/application.properties` with your database details:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the application**

   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at http://localhost:8080

## 📝 API Documentation

### Product Endpoints

- `GET /api/products` - Get all products (paginated)
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/search?name=keyword` - Search products by name

### Cart Endpoints

- `GET /api/carts/{userId}` - View user's cart
- `POST /api/carts/{userId}/add` - Add product to cart
- `PUT /api/carts/{userId}/update` - Update cart item quantity
- `DELETE /api/carts/{userId}/remove` - Remove item from cart

### Order Endpoints

- `POST /api/orders/{userId}/place` - Place order from cart
- `GET /api/orders/{userId}` - Get user's orders
- `GET /api/orders/{userId}/paginated` - Get paginated user orders
- `GET /api/orders/details/{orderId}` - Get order details
- `PUT /api/orders/{orderId}/status` - Update order status

## 🗃️ Database Schema

The application uses the following key database tables:

- **users**: Application users
- **products**: Product information (name, price, stock, etc.)
- **categories**: Product categories
- **carts**: User shopping carts
- **cart_items**: Items in shopping carts with quantity
- **orders**: User orders with status, addresses, payment info
- **order_items**: Items in orders with prices, quantities, subtotals

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

Developed by [Om Preetham Bandi]
