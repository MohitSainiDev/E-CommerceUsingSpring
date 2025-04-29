package com.ecommerce.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
