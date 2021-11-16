package com.furnitureStore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Cart;
import com.furnitureStore.entities.User;


@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
	public Iterable<Cart> findByUser(User user);
}