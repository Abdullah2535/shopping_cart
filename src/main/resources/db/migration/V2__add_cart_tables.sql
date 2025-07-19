create table cart
(
    id          BINARY(16) not null
        primary key,
    dateCreated DATE       null
);
create table CartItem
(
    id       LONG    not null,
    quantity INTEGER null,
    constraint CartItem_products_id_fk
        foreign key (id) references products (id)
            on update cascade on delete cascade
    constraint CartItem_Cart_id_fk
     foreign key ()

);




