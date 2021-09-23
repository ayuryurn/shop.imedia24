
CREATE TABLE stock
(
    id         DECIMAL (19,2) identity
       constraint pk_stock_id PRIMARY KEY ,
    quantity        int    NOT NULL

);

CREATE TABLE products
(
    sku         VARCHAR(16)     NOT NULL
       constraint pk_product_id PRIMARY KEY,
    name        VARCHAR(125)    NOT NULL,
    description VARCHAR(125),
    price       DECIMAL(19,2)           NOT NULL,
    created_at  TIMESTAMP     NOT NULL,
    updated_at  TIMESTAMP     NOT NULL,
    stock DECIMAL (19,2) NOT NULL,

    constraint product_stock foreign key (stock) references stock(id)

);

