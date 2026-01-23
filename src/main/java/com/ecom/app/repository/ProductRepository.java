package com.ecom.app.repository;

import com.ecom.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public class ProductRepository {
    public interface productRepository extends JpaRepository<Product,Long> {

    };
}
