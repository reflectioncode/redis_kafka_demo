CREATE TABLE Product (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price DECIMAL(10, 2) NOT NULL,
                         quantity INTEGER NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Product (name, description, price, quantity, created_at, updated_at) VALUES
                                                                                     ('Product 1', 'Description for product 1', 10.99, 100, NOW(), NOW()),
                                                                                     ('Product 2', 'Description for product 2', 20.49, 50, NOW(), NOW()),
                                                                                     ('Product 3', 'Description for product 3', 15.00, 200, NOW(), NOW());