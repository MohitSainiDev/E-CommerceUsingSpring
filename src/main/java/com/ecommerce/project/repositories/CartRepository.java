package com.ecommerce.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
