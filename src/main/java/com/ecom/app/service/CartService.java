package com.ecom.app.service;

import com.ecom.app.dto.CartItemRequest;
import com.ecom.app.model.CartItem;
import com.ecom.app.model.Product;
import com.ecom.app.model.User;
import com.ecom.app.repository.CartItemRepository;
import com.ecom.app.repository.ProductRepository;
import com.ecom.app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public void addToCart(Long userId, CartItemRequest request) {


        Product product = productRepository.findById(request.getProductId())  // fetch product by id or throw exception if product does not exist
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        User user = userRepository.findById(userId) // fetch user by id or throw exception if user does not exist
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product); //find existing cart item for the user and product


        int finalQuantity = (existingCartItem != null) // calculate final quantity (existing quantity + new request quantity)
                ? existingCartItem.getQuantity() + request.getQuantity()
                : request.getQuantity();


        if (finalQuantity > product.getStockQuantity()) { // validate that requested quantity does not exceed available stock
            throw new IllegalStateException("Product out of stock");
        }

        BigDecimal totalPrice =  product.getPrice().multiply(BigDecimal.valueOf(finalQuantity)); //  calculate total price based on final quantity

        if (existingCartItem == null) {
            existingCartItem = new CartItem();
            existingCartItem.setUser(user);
            existingCartItem.setProduct(product);
        existingCartItem.setQuantity(finalQuantity);
        existingCartItem.setPrice(totalPrice);
        }


        cartItemRepository.save(existingCartItem);
    }

    public boolean removeFromCart(String userId, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(Long.valueOf(userId));

        if (product.isPresent() && user.isPresent()) {
             CartItem cartItem = cartItemRepository.findByUserAndProduct(user.get(), product.get());
            if (cartItem != null) {
                cartItemRepository.deleteByUserAndProduct(user.get(), product.get());
                return true;
            }

        }
        return false;
    }

    public List<CartItem> getCartItems(String userId) {
       User user =  userRepository.findById(Long.valueOf(userId))
               .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return cartItemRepository.findByUser(user);


    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId))
                        .ifPresent(cartItemRepository::deleteByUser);

    }
}