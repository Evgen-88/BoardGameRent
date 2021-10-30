package com.itrex.konoplyanik.boardgamerent.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rent")
public class Rent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "board_game_id", insertable = false, updatable = false)
	private Long boardGameId;
	
	@Column(name = "order_id", insertable = false, updatable = false)
	private Long orderId;
	
	@Column(name = "rent_from")
	private LocalDate rentFrom;
	
	@Column(name = "rent_to")
	private LocalDate rentTo;
	
	@Column(name = "price")
	private Integer price;
	
	@ManyToOne
	@JoinColumn(name = "board_game_id")
	private BoardGame boardGame;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public Rent() {
	}
	
	public Rent(Long boardGameId, Long orderId, LocalDate rentFrom, LocalDate rentTo, Integer price) {
		this.boardGameId = boardGameId;
		this.orderId = orderId;
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.price = price;
	}
	
	public Rent(Long id, Long boardGameId, Long orderId, LocalDate rentFrom, LocalDate rentTo, Integer price) {
		this.id = id;
		this.boardGameId = boardGameId;
		this.orderId = orderId;
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.price = price;
	}
	
	public Rent(BoardGame boardGame, Order order, LocalDate rentFrom, LocalDate rentTo, Integer price) {
		this.boardGame = boardGame;
		this.order = order;
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBoardGameId() {
		return boardGameId;
	}
	public void setBoardGameId(Long boardGameId) {
		this.boardGameId = boardGameId;
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
	
	public BoardGame getBoardGame() {
		return boardGame;
	}

	public void setBoardGame(BoardGame boardGame) {
		this.boardGame = boardGame;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardGameId == null) ? 0 : boardGameId.hashCode());
		result = prime * result + ((rentFrom == null) ? 0 : rentFrom.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Rent other = (Rent) obj;
		if (boardGameId == null) {
			if (other.boardGameId != null)
				return false;
		} else if (!boardGameId.equals(other.boardGameId))
			return false;
		if (rentFrom == null) {
			if (other.rentFrom != null)
				return false;
		} else if (!rentFrom.equals(other.rentFrom))
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
		if (rentTo == null) {
			if (other.rentTo != null)
				return false;
		} else if (!rentTo.equals(other.rentTo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rent [id=" + id + ", boardGameId=" + boardGameId + ", orderId=" + orderId + ", rentFrom=" + rentFrom + ", rentTo="
				+ rentTo + ", price=" + price + "]";
	}
	
}
