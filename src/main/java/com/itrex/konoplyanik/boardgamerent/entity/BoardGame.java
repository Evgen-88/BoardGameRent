package com.itrex.konoplyanik.boardgamerent.entity;

public class BoardGame {
	
	private Long id;
	private String name;
	private Integer rentPrice;
	private Integer quantity;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "BoardGame [id=" + id + ", name=" + name + ", rentPrice=" + rentPrice + ", quantity=" + quantity + "]";
	}
	
}
