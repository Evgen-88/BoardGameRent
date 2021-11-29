package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
	
	@Query("from Accessory a left join fetch a.purchases where a.id =:id")
	Optional<Accessory> findAccessoryById(@Param("id") Long id);
}
