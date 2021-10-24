package com.itrex.konoplyanik.boardgamerent.entity;

public class BoardGame {
	
	private Long id;
	private String name;
	private Integer rentPrice;
	private Integer quantity;
	
	public BoardGame() {
	}
	
	public BoardGame(String name, Integer rentPrice, Integer quantity) {
		this.name = name;
		this.rentPrice = rentPrice;
		this.quantity = quantity;
	}
	
	public BoardGame(Long id, String name, Integer rentPrice, Integer quantity) {
		this.id = id;
		this.name = name;
		this.rentPrice = rentPrice;
		this.quantity = quantity;
	}
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((rentPrice == null) ? 0 : rentPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardGame other = (BoardGame) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (rentPrice == null) {
			if (other.rentPrice != null)
				return false;
		} else if (!rentPrice.equals(other.rentPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BoardGame [id=" + id + ", name=" + name + ", rentPrice=" + rentPrice + ", quantity=" + quantity + "]";
	}
	
}
