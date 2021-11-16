package com.furnitureStore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}