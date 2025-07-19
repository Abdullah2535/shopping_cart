package com.shoppingapi.shopping_cart.mappers;

import com.shoppingapi.shopping_cart.dto.ProductDto;
import com.shoppingapi.shopping_cart.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(source = "category.id",target = "categoryId")
  ProductDto toDto(Product product);

  //@Mapping(source = "categoryId",target = "category.id")
  Product toEntity(ProductDto dto);

  @Mapping(target ="id",ignore =true)
  void update(ProductDto productDto, @MappingTarget Product product);
}
