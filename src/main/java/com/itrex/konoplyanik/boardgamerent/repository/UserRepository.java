package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itrex.konoplyanik.boardgamerent.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("from User u left join fetch u.roles left join fetch u.orders where u.id = :id")
	Optional<User> findUserById(@Param("id") Long id);
	
}
