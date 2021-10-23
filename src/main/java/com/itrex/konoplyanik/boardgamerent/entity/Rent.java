package com.itrex.konoplyanik.boardgamerent.entity;

import java.time.LocalDate;

public class Rent {
	
	private Long id;
	private Long boardGameId;
	private Long orderId;
	private LocalDate from;
	private LocalDate to;
	private Integer price;
	
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
	public LocalDate getFrom() {
		return from;
	}
	public void setFrom(LocalDate from) {
		this.from = from;
	}
	public LocalDate getTo() {
		return to;
	}
	public void setTo(LocalDate to) {
		this.to = to;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Rent [id=" + id + ", boardGameId=" + boardGameId + ", orderId=" + orderId + ", from=" + from + ", to="
				+ to + ", price=" + price + "]";
	}
	
}
