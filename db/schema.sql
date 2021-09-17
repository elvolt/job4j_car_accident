CREATE TABLE IF NOT EXISTS type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS rule
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS accident
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100),
    text    VARCHAR(200),
    address VARCHAR(100),
    type_id INT NOT NULL REFERENCES type (id)
);

CREATE TABLE IF NOT EXISTS accident_rule
(
    accident_id INT REFERENCES accident (id),
    rule_id     INT REFERENCES rule (id),
    PRIMARY KEY (accident_id, rule_id)
);