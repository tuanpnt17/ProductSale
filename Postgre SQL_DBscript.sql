-- Tạo database
CREATE DATABASE sales_app_db;
\c sales_app_db;

-- Tạo bảng Users
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15),
    address VARCHAR(255),
    role VARCHAR(50) NOT NULL
);

-- Tạo bảng Categories
CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

-- Tạo bảng Products
CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    brief_description VARCHAR(255),
    full_description TEXT,
    technical_specifications TEXT,
    price DECIMAL(18, 2) NOT NULL,
    image_url VARCHAR(255),
    category_id INT REFERENCES categories(category_id)
);

-- Tạo bảng Carts
CREATE TABLE carts (
    cart_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    total_price DECIMAL(18, 2) NOT NULL,
    status VARCHAR(50) NOT NULL
);

-- Tạo bảng CartItems
CREATE TABLE cart_items (
    cart_item_id SERIAL PRIMARY KEY,
    cart_id INT REFERENCES carts(cart_id),
    product_id INT REFERENCES products(product_id),
    quantity INT NOT NULL,
    price DECIMAL(18, 2) NOT NULL
);

-- Tạo bảng Orders
CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    cart_id INT REFERENCES carts(cart_id),
    user_id INT REFERENCES users(user_id),
    payment_method VARCHAR(50) NOT NULL,
    billing_address VARCHAR(255) NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng Payments
CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(order_id),
    amount DECIMAL(18, 2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50) NOT NULL
);

-- Tạo bảng Notifications
CREATE TABLE notifications (
    notification_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    message VARCHAR(255),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng ChatMessages
CREATE TABLE chat_messages (
    chat_message_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    message TEXT,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng StoreLocations
CREATE TABLE store_locations (
    location_id SERIAL PRIMARY KEY,
    latitude DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL,
    address VARCHAR(255) NOT NULL
);
