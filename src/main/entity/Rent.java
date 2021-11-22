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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "rent")
public class Rent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "rent_from")
	private LocalDate rentFrom;
	
	@Column(name = "rent_to")
	private LocalDate rentTo;
	
	@Column(name = "price")
	private Integer price;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "board_game_id")
	private BoardGame boardGame;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Rent other = (Rent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

	
}
