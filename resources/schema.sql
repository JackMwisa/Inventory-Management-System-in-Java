CREATE DATABASE inventory_system;

USE inventory_system;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       type ENUM('CLIENT', 'MANAGER') NOT NULL,
                       balance DOUBLE DEFAULT 0
);

CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100),
                          type VARCHAR(50),
                          model VARCHAR(50),
                          manufacturer VARCHAR(100),
                          description TEXT,
                          selling_price DOUBLE,
                          purchasing_price DOUBLE,
                          expiring_year INT,
                          quantity INT
);

CREATE TABLE cart_items (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT,
                            product_id INT,
                            quantity INT,
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT,
                        product_id INT,
                        quantity INT,
                        total_price DOUBLE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (product_id) REFERENCES products(id)
);
