package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface RoleRepository {
	
	List<Role> findAll() throws RepositoryException;
	Role findById(Long id) throws RepositoryException;
	Role add(Role role) throws RepositoryException;
	Role update(Role role) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;

}
