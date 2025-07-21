create table carts
(
    id          BINARY(16) default (uuid_to_bin(uuid())) not null
        primary key,
    dateCreated DATE default (curdate())  not null
);
create table CartItem
(
    id       BIGINT    auto_increment primary key ,
    quantity int  null default 1,
    cart_id     BINARY(16) not null ,
    product_id  BIGINT not null ,
    constraint CartItem_products_id_fk
        foreign key (product_id) references products (id)
             on delete cascade,
     constraint CartItem_Cart_id_fk
     foreign key (cart_id) references carts (id)
          on delete cascade,
     constraint cart_items_cart_product_unique
    unique (cart_id,product_id)

);




