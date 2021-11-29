package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrex.konoplyanik.boardgamerent.converters.RoleConverter;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;
import com.itrex.konoplyanik.boardgamerent.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleDTO> findAll() {
		return roleRepository.findAll().stream().map(RoleConverter::convertRoleToDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleDTO> findRolesByUser(Long userId) throws ServiceException {
		return roleRepository.findRolesByUsers_id(userId).stream().map(RoleConverter::convertRoleToDTO)
				.collect(Collectors.toList());
	}
}
