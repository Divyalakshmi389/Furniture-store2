package com.furnitureStore.services;

import java.util.List;
import java.util.Optional;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.furnitureStore.entities.Premium;
import com.furnitureStore.entities.Category;
import com.furnitureStore.entities.Genre;
import com.furnitureStore.entities.Product;
import com.furnitureStore.entities.Result;
import com.furnitureStore.entities.Non_premium;
import com.furnitureStore.repositories.PremiumRepository;
import com.furnitureStore.repositories.CategoryRepository;
import com.furnitureStore.repositories.ProductRepository;
import com.furnitureStore.repositories.NonPremiumRepository;


@Repository
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private NonPremiumRepository NonPremiumRepository;
	
	@Autowired
	private PremiumRepository PremiumRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public void saveNonPremium(String type, String title, String artist, Double price, Category category, Genre genre,
			String description) {
		Product product = new Product();
		product.setPrice(price);
		product.setArtist(artist);
		product.setType(type);
		
		Category c1 = categoryRepository.findByNameAndGenre(category.getName(), genre);
		System.out.println("C1: " + c1);
		System.out.println("Category is : " + c1 + " and genre is " + genre);
		
		if (c1 == null) {
			c1 = new Category();
			c1.setName(category.getName());
			c1.setGenre(genre);
			categoryRepository.save(c1);
		}else {
			c1 = category;
		}
		product.setCategory(c1);
		
		//NonPremium
		Non_premium NonPremium = new Non_premium();
		NonPremium.setPrice(price);
		NonPremium.setTitle(title);
		NonPremium.setDescription(description);
		
		NonPremium.setProduct(product);
		productRepository.save(product);
		NonPremiumRepository.save(NonPremium);
		
	}
	
	public void savePremium(String type, String title, String artist, Double price, Category category, Genre genre,
			String description) {
		Product product = new Product();
		product.setPrice(price);
		product.setArtist(artist);
		product.setType(type);
		
		Category c1 = categoryRepository.findByNameAndGenre(category.getName(), genre);
		if (c1 == null) {
			c1 = new Category();
			c1.setName(category.getName());
			c1.setGenre(genre);
			categoryRepository.save(c1);
		}else {
			c1 = category;
		}
		product.setCategory(c1);
		
		Premium Premium = new Premium();
		Premium.setTitle(title);
		Premium.setDescription(description);
		
		Premium.setProduct(product);
		
		productRepository.save(product);
		PremiumRepository.save(Premium);
	}

	public Iterable<Result> getAll() {
		List<Result> results= new ArrayList<>();
		Iterable<Product> products = productRepository.findAll();
		for (Product product : products) {
			//System.out.println(product.getCategory());
			Result res = new Result();
			
			res.setPid(product.getId());
			res.setType(product.getType());
			res.setArtist(product.getArtist());
			res.setPrice(product.getPrice());
			res.setCategory(product.getCategory());
			
			if (product.getType().equals("NonPremium")) {
				Non_premium NonPremium = NonPremiumRepository.findByProduct(product);
				if (NonPremium != null) {
					res.setTid(NonPremium.getId());
					res.setTitle(NonPremium.getTitle());
					res.setDescription(NonPremium.getDescription());
				}
			}else if (product.getType().equals("Premium")){
				Premium Premium = PremiumRepository.findByProduct(product);
				if (Premium != null) {
//					System.out.println("Premium: " + Premium);
					res.setAid(Premium.getId());
					res.setTitle(Premium.getTitle());
					res.setDescription(Premium.getDescription());
				}
			}
			//System.out.println(res);
			results.add(res);
		}
		return results;
	}

	public Result findById(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			Result res = new Result();
			Product p = product.get();
			res.setPid(p.getId());
			res.setType(p.getType());
			res.setArtist(p.getArtist());
			res.setPrice(p.getPrice());
			res.setCategory(p.getCategory());
			res.setGenre(p.getCategory().getGenre());
			
			if (p.getType().equals("NonPremium")) {
				Non_premium NonPremium = NonPremiumRepository.findByProduct(p);
				if (NonPremium != null) {
					res.setTid(NonPremium.getId());
					res.setTitle(NonPremium.getTitle());
					res.setDescription(NonPremium.getDescription());
				}
			}else if (p.getType().equals("Premium")){
				Premium Premium = PremiumRepository.findByProduct(p);
				if (Premium != null) {
					res.setAid(Premium.getId());
					res.setTitle(Premium.getTitle());
					res.setDescription(Premium.getDescription());
				}
			}
			
			return res;
		}
		return null;
	}

	public void updateNonPremium(Result res) {
		
		Category c1 = categoryRepository.findByNameAndGenre(res.getCategory().getName(), res.getGenre());
		System.out.println("C1: " + c1);
		System.out.println("Category is : " + c1 + " and genre is " + res.getGenre());

		if (c1 == null) {
			c1 = new Category();
			c1.setName(res.getCategory().getName());
			c1.setGenre(res.getCategory().getGenre());
			categoryRepository.save(c1);
		}
		
		
		Optional<Product> product = productRepository.findById(res.getPid());
		if (product.isPresent()) {
			product.get().setPrice(res.getPrice());
			product.get().setArtist(res.getArtist());
			product.get().setCategory(c1);
			productRepository.save(product.get());
		}
		
		Optional<Non_premium> NonPremium = NonPremiumRepository.findById(res.getTid());
		if (NonPremium.isPresent()) {
			NonPremium.get().setTitle(res.getTitle());
			NonPremium.get().setDescription(res.getDescription());
			NonPremium.get().setPrice(res.getPrice());
			if (product.isPresent())
				NonPremium.get().setProduct(product.get());
			NonPremiumRepository.save(NonPremium.get());
		}
	}

	public void updatePremium(Result res) {
		Category c1 = categoryRepository.findByNameAndGenre(res.getCategory().getName(), res.getGenre());
		if (c1 == null) {
			c1 = new Category();
			c1.setName(res.getCategory().getName());
			c1.setGenre(res.getGenre());
			categoryRepository.save(c1);	
		}
		
		
		Optional<Product> product = productRepository.findById(res.getPid());
		if (product.isPresent()) {
			product.get().setPrice(res.getPrice());
			product.get().setArtist(res.getArtist());
			product.get().setCategory(c1);
			
			productRepository.save(product.get());
		}
		
		Optional<Premium> Premium = PremiumRepository.findById(res.getAid());
		if (Premium.isPresent()) {
			Premium.get().setTitle(res.getTitle());
			Premium.get().setDescription(res.getDescription());
			if (product.isPresent())
				Premium.get().setProduct(product.get());
			PremiumRepository.save(Premium.get());
		}
		
	}

	public void deleteProduct(Result res) {
		if (res.getType().indexOf("NonPremium") != -1) {
			NonPremiumRepository.deleteById(res.getTid());
		}else {
			PremiumRepository.deleteById(res.getAid());
		}
		
		productRepository.deleteById(res.getPid());
	}
	
	
}