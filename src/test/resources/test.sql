CREATE TABLE products
(
    id         bigint,
    name       varchar(255),
    price      decimal,
    sale_types varchar(255)[]
);

CREATE TABLE discount_cards
(
    card_number varchar(255),
    discount    decimal
);

INSERT INTO products (id, name, price, sale_types)
VALUES (1, 'Milk', 2.0, '{}'::varchar[]),
       (2, 'Bread', 3.0, '{TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS}'::varchar[]),
       (3, 'Meat', 15.0, '{}'::varchar[]),
       (4, 'Cheese', 5.0, '{TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS}'::varchar[]),
       (5, 'Potato', 4.0, '{}'::varchar[]);

INSERT INTO discount_cards (card_number, discount)
VALUES ('0000', 0.0),
       ('1111', 0.03),
       ('2222', 0),
       ('3333', 0.05),
       ('4444', 0.07),
       ('5555', 0.09);
