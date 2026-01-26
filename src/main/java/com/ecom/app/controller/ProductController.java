package com.ecom.app.controller;

import com.ecom.app.dto.ProductRequest;
import com.ecom.app.dto.ProductResponse;
import com.ecom.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    @PostMapping
     public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
     return new ResponseEntity<>(productService.createProduct(productRequest),
             HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> fetchAllProduct(){
        return ResponseEntity.ok(productService.fetchAllProduct());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct( @PathVariable Long id,  @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id , productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
       boolean deletedProduct = productService.deleteProduct(id);
       return  deletedProduct ? ResponseEntity.noContent().build() :  ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProducts(keyword));

    }

}
