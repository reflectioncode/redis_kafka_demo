CREATE TABLE Product (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price DECIMAL(10, 2) NOT NULL,
                         quantity INTEGER NOT NULL,
                         createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Product (name, description, price, quantity) VALUES
                                                                                   ('Product 1', 'Description for product 1', 10.99, 100),
                                                                                   ('Product 2', 'Description for product 2', 20.49, 50),
                                                                                   ('Product 3', 'Description for product 3', 15.00, 200);