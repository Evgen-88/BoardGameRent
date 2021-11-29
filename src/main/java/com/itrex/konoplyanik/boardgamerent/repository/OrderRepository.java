package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itrex.konoplyanik.boardgamerent.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query("from Order o left join fetch o.user left join fetch o.purchases left join fetch o.rents where o.id = :id")
	Optional<Order> findOrderById(@Param("id") Long id);
	
	List<Order> findOrdersByUser_id(Long id);

}
