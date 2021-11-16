package com.furnitureStore.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Premium;
import com.furnitureStore.entities.Product;


@Repository
public interface PremiumRepository extends CrudRepository<Premium, Integer>{
	public Premium findByProduct(Product product);

}