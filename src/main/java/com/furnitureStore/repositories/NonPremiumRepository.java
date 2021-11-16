package com.furnitureStore.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Product;
import com.furnitureStore.entities.Non_premium;


@Repository
public interface NonPremiumRepository extends CrudRepository<Non_premium, Integer>{
	public Non_premium findByProduct(Product product);

}