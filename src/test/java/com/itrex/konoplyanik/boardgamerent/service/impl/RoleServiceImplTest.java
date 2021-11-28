package com.itrex.konoplyanik.boardgamerent.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;
import com.itrex.konoplyanik.boardgamerent.service.BaseServiceTest;

@SpringBootTest
public class RoleServiceImplTest extends BaseServiceTest {

	@InjectMocks
    private RoleServiceImpl roleService;
	@Mock
	private RoleRepository roleRepository;
    
    @Test
    public void findAll_validData_shouldReturnRoleList() throws RepositoryException, ServiceException {
        //given
        int expected = 3;
        //when
        Mockito.when(roleRepository.findAll()).thenReturn(Arrays.asList(new Role(), new Role(), new Role()));
        List<RoleDTO> actual = roleService.findAll();
        //then
        Assert.assertEquals(expected, actual.size());
    }
    
    @Test
	public void findRolesByUser_validData_shouldReturnRoleDTO() throws RepositoryException, ServiceException {
		//given
		Role role = getRoles().get(0);
        List<Role> roles = new ArrayList<>() {{
	       	add(role);
        }};
        List<RoleDTO> expected = new ArrayList<>() {{
	       	add(RoleDTO.builder()
	       			.id(role.getId())
	       			.name(role.getName())
		       		.build());
	    }};
		// when
		Mockito.when(roleRepository.findRolesByUser(1L)).thenReturn(roles);
		List<RoleDTO> actual = roleService.findRolesByUser(1L);
		// then
		Assert.assertEquals(expected, actual);
	}

}
