```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO users (username, password)
VALUES ('test', '1234');

INSERT INTO products (name, price)
VALUES
    ('T-shirt', 199.00),
    ('Hoodie', 499.00),
    ('Keps', 149.00);