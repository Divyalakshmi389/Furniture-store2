package com.furnitureStore.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cart")
@Data
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="is_purchased")
	private Boolean isPurchased;
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {
				CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH	
			})
	@JoinTable(
				name="cart_product",
				joinColumns=@JoinColumn(name="cart_id"),
				inverseJoinColumns=@JoinColumn(name="product_id")
			)
	private List<Product> products;
	
	public void addProduct(Product product) {
		if (products == null) {
			products = new ArrayList<>();
		}
		products.add(product);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsPurchased() {
		return isPurchased;
	}

	public void setIsPurchased(Boolean isPurchased) {
		this.isPurchased = isPurchased;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}