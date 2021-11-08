package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;

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
	public RoleDTO add(RoleDTO roleDTO) throws ServiceException {
		try {
			return Converter.convertRoleToDTO(roleRepository.add(Converter.convertRoleToEntity(roleDTO)));
		} catch (RepositoryException ex) {
			throw new ServiceException("Error: add: " + ex);
		}
	}

	@Override
	public boolean delete(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoleDTO> findRolesByUser(Long userId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
