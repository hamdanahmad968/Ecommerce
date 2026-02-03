package com.ecom.app.repository;

import com.ecom.app.model.CartItem;
import com.ecom.app.model.Product;
import com.ecom.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem,Long> {

    CartItem findByUserAndProduct(User user, Product product);

    @Modifying
    void deleteByUserAndProduct(User user, Product product);

         List<CartItem> findByUser(User user);
}
