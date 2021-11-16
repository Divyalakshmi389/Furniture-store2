package com.furnitureStore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Cart;
import com.furnitureStore.entities.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{
	public Transaction findByCart(Cart cart);
}