CREATE TABLE elko_product_info_json
(
    epij_id serial NOT NULL,
    elko_product_json json NOT NULL,
    PRIMARY KEY (epij_id)
);