-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create categories table
CREATE TABLE IF NOT EXISTS categories (
    id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    category_name VARCHAR(255) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create revenue table
CREATE TABLE IF NOT EXISTS revenue (
    id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR(255) NOT NULL,
    hours_worked VARCHAR(25) NOT NULL,
    curr_day TIMESTAMP NOT NULL,
    currency VARCHAR(3) NOT NULL,
    category_id INT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);