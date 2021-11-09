package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;
import com.itrex.konoplyanik.boardgamerent.service.RoleService;
import com.itrex.konoplyanik.boardgamerent.util.Converter;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleDTO> findAll() throws ServiceException {
		try {
			return roleRepository.findAll().stream()
					.map(Converter::convertRoleToDTO)
					.collect(Collectors.toList());
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: findAll: " + ex);
		}
	}

}
