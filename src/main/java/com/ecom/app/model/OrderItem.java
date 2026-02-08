package com.ecom.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID , PRODUCT_ID , QUANTITY , PRICE , ORDER(1 ORDER CAN have many order item)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id" , nullable = false)
    private Product product;

    private Integer quantity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id" , nullable = false)
    private Order order;



}