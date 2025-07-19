package com.shoppingapi.shopping_cart.Controllers;


import com.shoppingapi.shopping_cart.dto.ProductDto;
import com.shoppingapi.shopping_cart.entities.Product;
import com.shoppingapi.shopping_cart.mappers.ProductMapper;
import com.shoppingapi.shopping_cart.repositories.CategoryRepository;
import com.shoppingapi.shopping_cart.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping("")
    public Iterable<ProductDto> getAllProducts(@RequestParam (required = false )Byte categoryId ) {
         List<Product> products;
         if (categoryId != null){
             products = productRepository.findByCategoryId(categoryId);
         }
         else
             products = productRepository.findAllWithCategory();
       return products.stream().map(productMapper::toDto) .toList();
    }
     @GetMapping("/{id}")

    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {

        var product= productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        //  var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping("/{new-product}")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto ,
                                                   UriComponentsBuilder uriBuilder) {

        var category=  categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());
        var uri   = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,@RequestBody ProductDto productDto) {
        var category =  categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.badRequest().build();
        }
        product.setCategory(category);
        productMapper.update(productDto,product);
        productRepository.save(product);
        return ResponseEntity.ok(productDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.badRequest().build();

        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

}
