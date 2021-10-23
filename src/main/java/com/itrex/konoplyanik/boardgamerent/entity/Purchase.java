package com.itrex.konoplyanik.boardgamerent.entity;

public class Purchase {

	private Long id;
	private Long accessoryId;
	private Long orderId;
	private Integer quantity;
	private Integer price;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccessoryId() {
		return accessoryId;
	}
	public void setAccessoryId(Long accessoryId) {
		this.accessoryId = accessoryId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Purchase [id=" + id + ", accessoryId=" + accessoryId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}
	
}
