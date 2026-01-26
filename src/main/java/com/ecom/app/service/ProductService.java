package com.ecom.app.service;
import com.ecom.app.dto.ProductRequest;
import com.ecom.app.dto.ProductResponse;
import com.ecom.app.model.Product;
import com.ecom.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductResponse createProduct(ProductRequest productRequest) {// This method is called by the controller when a client wants to create a new product.It receives product data already filled by Spring from the incoming JSON.
        Product product = new Product();// Create a new empty Product entity. This object represents a row that will be saved in the database.
        updateProductFromRequest(product, productRequest);// Copy allowed data from the ProductRequest (client input) into the Product entity (database object).
        Product savedProduct = productRepository.save(product); //Save the Product entity into the database. Hibernate inserts a new row and generates an ID.
        return mapToProductResponse(savedProduct);// Convert the saved Product entity into a ProductResponse DTO This DTO is safe to send back to the client.
    }


    public List<ProductResponse> fetchAllProduct() {
        return productRepository.findByActiveTrue()
                .stream().map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }


    public Optional <ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)   // .orElseThrow(() -> new RuntimeException("Product not found")); if no optional , no map required
                .map(exisitingProduct -> {
                    updateProductFromRequest(exisitingProduct, productRequest);
                    Product savedProduct = productRepository.save(exisitingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }
    public Boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());


    }




    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());

    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setPrice(savedProduct.getPrice());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setActive(savedProduct.getActive());
        return response;

    }


}
