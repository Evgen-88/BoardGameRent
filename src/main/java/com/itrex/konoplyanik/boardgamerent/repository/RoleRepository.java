package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itrex.konoplyanik.boardgamerent.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findRolesByUsers_id(Long userId);
}
