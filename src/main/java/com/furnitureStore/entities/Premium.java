package com.furnitureStore.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="premium")
@Data
@NoArgsConstructor
public class Premium {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="premium_id")
    private Integer id;
    
    @Column(name="title")
    private String title;
    
    @Column(name="description")
    private String description;
    
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}