package com.furnitureStore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Genre;


@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer>{
	public Genre findByName(String name);
}