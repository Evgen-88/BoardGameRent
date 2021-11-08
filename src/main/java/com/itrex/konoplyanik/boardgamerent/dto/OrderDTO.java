package com.itrex.konoplyanik.boardgamerent.dto;

import java.time.LocalDate;
import java.util.List;
import com.itrex.konoplyanik.boardgamerent.entity.Status;

public class OrderDTO {

	private Long id;
	private Integer totalPrice;
	private LocalDate date;
	private Status status;
	private List<PurchaseDTO> purchases;
	private List<RentDTO> rents;
	private UserDTO user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<PurchaseDTO> getPurchases() {
		return purchases;
	}
	public void setPurchases(List<PurchaseDTO> purchases) {
		this.purchases = purchases;
	}
	public List<RentDTO> getRents() {
		return rents;
	}
	public void setRents(List<RentDTO> rents) {
		this.rents = rents;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((purchases == null) ? 0 : purchases.hashCode());
		result = prime * result + ((rents == null) ? 0 : rents.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		OrderDTO other = (OrderDTO) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (purchases == null) {
			if (other.purchases != null)
				return false;
		} else if (!purchases.equals(other.purchases))
			return false;
		if (rents == null) {
			if (other.rents != null)
				return false;
		} else if (!rents.equals(other.rents))
			return false;
		if (status != other.status)
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", totalPrice=" + totalPrice + ", date=" + date + ", status=" + status
				+ ", purchases=" + purchases + ", rents=" + rents + ", user=" + user + "]";
	}
	
}
