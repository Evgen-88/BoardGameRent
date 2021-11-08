package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.dto.AccessoryListDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
import com.itrex.konoplyanik.boardgamerent.service.AccessoryService;
import com.itrex.konoplyanik.boardgamerent.util.Converter;

@Service
public class AccessoryServiceImpl implements AccessoryService {

	private final AccessoryRepository accessoryRepository;

	public AccessoryServiceImpl(AccessoryRepository accessoryRepository) {
		this.accessoryRepository = accessoryRepository;
	}

	@Override
	public List<AccessoryListDTO> findAll() throws ServiceException {
		try {
			return accessoryRepository.findAll().stream()
					.map(accessory -> Converter.convertAccessoryToListDTO(accessory))
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findAll: " + ex);
		}
	}

	@Override
	public AccessoryDTO findById(Long id) throws ServiceException {
		try {
			return Converter.convertAccessoryToDTO(accessoryRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public AccessoryListDTO findListById(Long id) throws ServiceException {
		try {
			return Converter.convertAccessoryToListDTO(accessoryRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findListById: " + ex);
		}
	}

	@Override
	public AccessoryDTO add(AccessoryDTO accessory) throws ServiceException {
		try {
			return Converter.convertAccessoryToDTO(accessoryRepository.add(Converter.convertAccessoryToEntity(accessory)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public AccessoryDTO update(AccessoryDTO accessory) throws ServiceException {
		try {
			return Converter.convertAccessoryToDTO(accessoryRepository.update(Converter.convertAccessoryToEntity(accessory)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		try {
			return accessoryRepository.delete(id);
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: update: " + ex);
		}
	}

}