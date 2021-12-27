package com.itrex.konoplyanik.boardgamerent.repository;


import com.itrex.konoplyanik.boardgamerent.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
