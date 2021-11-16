package com.furnitureStore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Category;
import com.furnitureStore.entities.Genre;



@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{
	public Category findByNameAndGenre(String name, Genre genre);
}