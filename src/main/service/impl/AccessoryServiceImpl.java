package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.converters.AccessoryConverter;
import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
import com.itrex.konoplyanik.boardgamerent.service.AccessoryService;

@Service
public class AccessoryServiceImpl implements AccessoryService {

	private final AccessoryRepository accessoryRepository;

	public AccessoryServiceImpl(AccessoryRepository accessoryRepository) {
		this.accessoryRepository = accessoryRepository;
	}

	@Override
	public List<AccessoryDTO> findAll() throws ServiceException {
		try {
			return accessoryRepository.findAll().stream()
					.map(AccessoryConverter::convertAccessoryToDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findAll: " + ex);
		}
	}

	@Override
	public AccessoryDTO findById(Long id) throws ServiceException {
		try {
			return AccessoryConverter.convertAccessoryToDTO(accessoryRepository.findById(id));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findById: " + ex);
		}
	}

	@Override
	public AccessoryDTO add(AccessoryDTO accessory) throws ServiceException {
		try {
			return AccessoryConverter.convertAccessoryToDTO(accessoryRepository.add(AccessoryConverter.convertAccessoryToEntity(accessory)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public AccessoryDTO update(AccessoryDTO accessory) throws ServiceException {
		try {
			return AccessoryConverter.convertAccessoryToDTO(accessoryRepository.update(AccessoryConverter.convertAccessoryToEntity(accessory)));
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
