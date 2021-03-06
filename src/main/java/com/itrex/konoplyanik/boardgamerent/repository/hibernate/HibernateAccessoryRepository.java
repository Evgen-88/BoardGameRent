package com.itrex.konoplyanik.boardgamerent.repository.hibernate;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

@Deprecated
public interface HibernateAccessoryRepository {

	List<Accessory> findAll() throws RepositoryException;
	Accessory findById(Long id) throws RepositoryException;
	Accessory add(Accessory accessory) throws RepositoryException;
	Accessory update(Accessory accessory) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
}
