CREATE TABLE orders (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    order_time DATETIME NOT NULL
);

CREATE TABLE participants (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    order_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE expenses (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    value DOUBLE NOT NULL,
    order_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE vats (
	id INT PRIMARY KEY AUTO_INCREMENT,
    vat_type VARCHAR(30) NOT NULL,
    value DOUBLE NOT NULL,
	order_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE participants_expenses (
	participant_id INT,
    expense_id INT,
    value DOUBLE NOT NULL,
    PRIMARY KEY (participant_id, expense_id)
);

SELECT * FROM orders;
SELECT * FROM participants;
SELECT * FROM expenses;
SELECT * FROM vats;
DESCRIBE vats;
SELECT * FROM participants_expenses;

INSERT INTO orders(name, order_time)
VALUES ('El Sultan', '2021-07-06 22:00:00');

INSERT INTO participants(name, order_id)
VALUES ('Pola', 1), ('Andria', 1), ('Mario', 1);

INSERT INTO expenses(value, order_id)
VALUES (50, 1), (60, 1);
 
INSERT INTO vats(id, vat_type, value, order_id)
VALUES (1, 'Tax', 0.14, 1);

ALTER TABLE vats
MODIFY value DOUBLE DEFAULT 0.0;

DELETE FROM vats
WHERE id = 1;
 
INSERT INTO participants_expenses(participant_id, expense_id, value)
VALUES (1, 1, 25), (2, 1, 25), (3, 2, 60);

SELECT o.name, p.name FROM orders o
LEFT JOIN vats v ON v.order_id = o.id
JOIN participants p ON p.order_id = o.id
JOIN participants_expenses pe ON p.id = pe.participant_id
GROUP BY o.name, p.name;