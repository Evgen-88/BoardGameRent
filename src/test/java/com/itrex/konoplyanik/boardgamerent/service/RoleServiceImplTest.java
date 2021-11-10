package com.itrex.konoplyanik.boardgamerent.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import com.itrex.konoplyanik.boardgamerent.config.TestContextConfiguration;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.exception.RepositoryException;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;
import com.itrex.konoplyanik.boardgamerent.service.impl.RoleServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = TestContextConfiguration.class)
public class RoleServiceImplTest {

	@InjectMocks
    private RoleServiceImpl roleService;
	@Mock
	private RoleRepository roleRepository;
    
    @Test
    public void findAll_validData_shouldReturnRoleList() throws RepositoryException {
        //given
        int expected = 3;
        //when
        Mockito.when(roleService.findAll()).thenReturn(Arrays.asList(new RoleDTO(), new RoleDTO(), new RoleDTO()));
        List<Role> actual = roleRepository.findAll();
        //then
        Assert.assertEquals(expected, actual.size());
    }
}
