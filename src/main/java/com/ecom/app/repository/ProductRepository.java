package com.ecom.app.repository;

import com.ecom.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {

    List<Product> findByActiveTrue();

    @Query("SELECT p from Product p where p.active = true and p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%' , :Keyword, '%'))")
   List<Product> searchProducts (@Param("Keyword") String Keyword);
}
