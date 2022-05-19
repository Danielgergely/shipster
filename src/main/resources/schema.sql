CREATE SEQUENCE IF NOT EXISTS address_id_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;
/*CREATE TABLE IF NOT EXISTS address
(
    id      BIGINT  NOT NULL PRIMARY KEY,
    street  VARCHAR NOT NULL,
    number  VARCHAR NOT NULL,
    city    VARCHAR NOT NULL,
    zip     VARCHAR NOT NULL,
    country VARCHAR NOT NULL
);*/
ALTER SEQUENCE address_id_seq OWNED BY address.id;

CREATE SEQUENCE IF NOT EXISTS shipster_user_user_id_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;
/*CREATE TABLE IF NOT EXISTS shipster_user
(
    user_id    BIGINT  NOT NULL PRIMARY KEY,
    user_name  VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    email      VARCHAR NOT NULL,
    password   VARCHAR NOT NULL,
    address_id BIGINT  NOT NULL,
    gender     VARCHAR NOT NULL,
    roles      VARCHAR NOT NULL
);*/
ALTER SEQUENCE shipster_user_user_id_seq OWNED BY shipster_user.user_id;

CREATE SEQUENCE IF NOT EXISTS article_id_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;
/*CREATE TABLE IF NOT EXISTS article
(
    id           BIGINT  NOT NULL PRIMARY KEY,
    name         VARCHAR NOT NULL,
    description  VARCHAR NOT NULL,
    image_url    VARCHAR NOT NULL,
    price        FLOAT   NOT NULL,
    pallet_space FLOAT   NOT NULL,
    max_stack    FLOAT NOT NULL
);*/
ALTER SEQUENCE article_id_seq OWNED BY article.id;

CREATE SEQUENCE IF NOT EXISTS shipster_order_id_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;
/*CREATE TABLE IF NOT EXISTS shipster_order
(
    id               BIGINT  NOT NULL PRIMARY KEY,
    user_id          BIGINT  NOT NULL,
    order_status     VARCHAR NOT NULL,
    last_update_date DATE,
    basket_date      DATE,
    order_date       DATE,
    shipping_date    DATE,
    delivery_date    DATE,
    cancellation_date DATE
);*/
ALTER SEQUENCE shipster_order_id_seq OWNED BY shipster_order.id;

CREATE SEQUENCE IF NOT EXISTS order_item_id_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;
/*CREATE TABLE IF NOT EXISTS order_item
(
    id         BIGINT  NOT NULL PRIMARY KEY,
    article_id BIGINT  NOT NULL,
    order_id   BIGINT  NOT NULL,
    quantity   INTEGER NOT NULL
);*/
ALTER SEQUENCE order_item_id_seq OWNED BY order_item.id;

CREATE SEQUENCE IF NOT EXISTS provider_id_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;
/*CREATE TABLE IF NOT EXISTS provider
(
    id   BIGINT  NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);*/
ALTER SEQUENCE provider_id_seq OWNED BY provider.id;

CREATE SEQUENCE IF NOT EXISTS cost_id_seq START WITH 1 INCREMENT BY 1 MINVALUE 1;
/*CREATE TABLE IF NOT EXISTS cost
(
    id          BIGINT  NOT NULL PRIMARY KEY,
    provider_id BIGINT  NOT NULL,
    pallet      INTEGER NOT NULL,
    km          INTEGER NOT NULL,
    price       FLOAT   NOT NULL
);*/
ALTER SEQUENCE cost_id_seq OWNED BY cost.id;
