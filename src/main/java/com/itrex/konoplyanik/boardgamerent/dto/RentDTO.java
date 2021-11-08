package com.itrex.konoplyanik.boardgamerent.dto;

import java.time.LocalDate;

public class RentDTO {

	private Long id;
	private Long orderId;
	private LocalDate rentFrom;
	private LocalDate rentTo;
	private Integer price;
	private BoardGameListDTO boardGameList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public LocalDate getRentFrom() {
		return rentFrom;
	}
	public void setRentFrom(LocalDate rentFrom) {
		this.rentFrom = rentFrom;
	}
	public LocalDate getRentTo() {
		return rentTo;
	}
	public void setRentTo(LocalDate rentTo) {
		this.rentTo = rentTo;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public BoardGameListDTO getBoardGameList() {
		return boardGameList;
	}
	public void setBoardGameList(BoardGameListDTO boardGameList) {
		this.boardGameList = boardGameList;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardGameList == null) ? 0 : boardGameList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((rentFrom == null) ? 0 : rentFrom.hashCode());
		result = prime * result + ((rentTo == null) ? 0 : rentTo.hashCode());
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
		RentDTO other = (RentDTO) obj;
		if (boardGameList == null) {
			if (other.boardGameList != null)
				return false;
		} else if (!boardGameList.equals(other.boardGameList))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (rentFrom == null) {
			if (other.rentFrom != null)
				return false;
		} else if (!rentFrom.equals(other.rentFrom))
			return false;
		if (rentTo == null) {
			if (other.rentTo != null)
				return false;
		} else if (!rentTo.equals(other.rentTo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RentDTO [id=" + id + ", orderId=" + orderId + ", rentFrom=" + rentFrom + ", rentTo=" + rentTo
				+ ", price=" + price + ", boardGameList=" + boardGameList + "]";
	}
	
}
