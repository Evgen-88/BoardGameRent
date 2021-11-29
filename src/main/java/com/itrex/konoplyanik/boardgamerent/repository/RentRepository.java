package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itrex.konoplyanik.boardgamerent.entity.Rent;

public interface RentRepository extends JpaRepository<Rent, Long> {

	List<Rent> findRentsByOrder_id(Long id);
}
