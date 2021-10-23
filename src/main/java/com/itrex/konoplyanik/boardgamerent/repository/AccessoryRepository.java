package com.itrex.konoplyanik.boardgamerent.repository;

import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;

public interface AccessoryRepository {

	List<Accessory> findAll() throws RepositoryException;
	Accessory findById(Long id) throws RepositoryException;
	List<Accessory> addAll(List<Accessory> accessories) throws RepositoryException;
	Accessory add(Accessory accessory) throws RepositoryException;
	Accessory update(Accessory accessory) throws RepositoryException;
	boolean delete(Long id) throws RepositoryException;
}
