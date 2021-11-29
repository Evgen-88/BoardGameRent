package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrex.konoplyanik.boardgamerent.converters.AccessoryConverter;
import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.AccessoryRepository;
import com.itrex.konoplyanik.boardgamerent.repository.PurchaseRepository;
import com.itrex.konoplyanik.boardgamerent.service.AccessoryService;

@Service
public class AccessoryServiceImpl implements AccessoryService {

	private final AccessoryRepository accessoryRepository;
	private final PurchaseRepository purchaseRepository;

	public AccessoryServiceImpl(AccessoryRepository accessoryRepository, PurchaseRepository purchaseRepository) {
		this.accessoryRepository = accessoryRepository;
		this.purchaseRepository = purchaseRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessoryDTO> findAll() {
		return accessoryRepository.findAll().stream().map(AccessoryConverter::convertAccessoryToDTO)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public AccessoryDTO findById(Long id) {
		return accessoryRepository.findById(id).map(AccessoryConverter::convertAccessoryToDTO)
				.orElseThrow(() -> new ServiceException("Accessory not found"));
	}

	@Override
	@Transactional
	public AccessoryDTO add(AccessoryDTO accessory) {
		return AccessoryConverter.convertAccessoryToDTO(
				accessoryRepository.save(AccessoryConverter.convertAccessoryToEntity(accessory)));
	}

	@Override
	@Transactional
	public AccessoryDTO update(AccessoryDTO accessoryDTO) throws ServiceException {
		Accessory accessory = accessoryRepository.findById(accessoryDTO.getId())
				.orElseThrow(() -> new ServiceException("Accessory not found"));
		accessory.setName(accessoryDTO.getName());
		accessory.setPrice(accessoryDTO.getPrice());
		accessory.setQuantity(accessoryDTO.getQuantity());
		accessoryRepository.flush();
		return AccessoryConverter.convertAccessoryToDTO(accessoryRepository.findById(accessoryDTO.getId()).get());
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws ServiceException {
		Accessory accessory = accessoryRepository.findAccessoryById(id)
				.orElseThrow(() -> new ServiceException("Accessory not found"));
		for (Purchase purchase : accessory.getPurchases()) {
			purchaseRepository.delete(purchase);
		}
		accessoryRepository.delete(accessory);
		return true;
	}

}
