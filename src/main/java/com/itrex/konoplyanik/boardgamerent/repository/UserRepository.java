package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface UserRepository {
	
	List<User> findAll() throws RepositoryException;
	User findById(Long id) throws RepositoryException;
	List<User> addAll(List<User> users, List<Long> roleIds) throws RepositoryException;
	User add(User user, List<Long> roleIds) throws RepositoryException;
	User update(User user) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
	
	List<User> findUsersByRole(Long id) throws RepositoryException;
	boolean deleteRoleFromUser(Long userId, Long roleId) throws RepositoryException;
	Role addRoleToUser(Long userId, Long roleId) throws RepositoryException;
	
}
