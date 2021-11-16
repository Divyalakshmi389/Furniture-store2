package com.furnitureStore.entities;


import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="non_premium")
@Data
@NoArgsConstructor
public class Non_premium {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="nonpremium_id")
    private Integer id;

    @Column(name="title")
    private String title;
    
    @Column(name="description")
    private String description;
    
    @Column(name="price")
    private Double price;
    
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}