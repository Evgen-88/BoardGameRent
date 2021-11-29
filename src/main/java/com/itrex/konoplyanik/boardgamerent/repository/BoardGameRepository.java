package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

	@Query("from BoardGame a left join fetch a.rents where a.id =:id")
	Optional<BoardGame> findBoardGameById(@Param("id") Long id);
}
