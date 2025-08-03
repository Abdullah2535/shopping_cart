CREATE TABLE IF NOT EXISTS orders (
                                               `id` BIGINT NOT NULL AUTO_INCREMENT,
                                               `customer_id` BIGINT  NOT NULL,
                                               `status` VARCHAR(20) NOT NULL,
                                               `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
                                               `total_price` DECIMAL(10,2) NOT NULL,
                                               PRIMARY KEY (`id`),
                                               CONSTRAINT fk_order_users_id
                                  foreign key (customer_id) references users (id)
                                  );


-- -----------------------------------------------------
-- Table `order_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS order_items (
                                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                    `product_id` BIGINT NOT NULL,
                                                    `unit_price` DECIMAL(10,2) NOT NULL,
                                                    `quantity` INT NOT NULL,
                                                    `total_price` DECIMAL(10,2) NOT NULL,
                                                    `orders_id` BIGINT NOT NULL,
                                                    PRIMARY KEY (id),
#                                                     INDEX `fk_order_items_orders_idx` (`orders_id` ASC) VISIBLE,
                                                    CONSTRAINT `fk_order_items_orders`
                                                        FOREIGN KEY (orders_id)
                                                            REFERENCES orders (id),
                                                    CONSTRAINT fk_order_items_products
                                       foreign key (product_id) references products (id)

                                       );