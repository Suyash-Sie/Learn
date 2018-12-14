package com.iris.webapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "food")
public class Food implements Serializable {

	private static final long serialVersionUID = -1000119078147252957L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11, nullable = false)
	private int id;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Lob
	@Column(name = "image", length = Integer.MAX_VALUE, nullable = true)
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Column(name = "description", length = 500, nullable = true)
	private String description;

	@ManyToOne
	@JoinColumn(name = "restId")
	private Restaurant restaurant;

	public Food() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}