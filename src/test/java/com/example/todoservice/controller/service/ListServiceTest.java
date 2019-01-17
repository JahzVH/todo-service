package com.example.todoservice.controller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.todoservice.controller.TestMockData;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.model.List;
import com.example.todoservice.model.User;
import com.example.todoservice.repository.ListRepository;
import com.example.todoservice.repository.UserRepository;
import com.example.todoservice.service.ListService;

@RunWith(SpringRunner.class)
public class ListServiceTest extends TestMockData {

	@InjectMocks
	private ListService listService;

	@MockBean
	private ListRepository listRepository;

	@MockBean
	private UserRepository userRepository;

	private Optional<List> listData;
	
	private Optional<User> userData;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		// Init common data for mocks
		initData();

		listData = Optional.of(list);
		userData = Optional.of(user);
		authentication = mock(Authentication.class);
		when(userRepository.findOneByUsername(any())).thenReturn(userData);
		when(listRepository.save(any(List.class))).thenReturn(list);
		when(listRepository.findOneByListIdAndUser(any(Integer.class), any(User.class))).thenReturn(listData);
	}

	@Test
	public void testCreateList_ResultOk() throws Exception {
		List list = listService.createList(listRequest, authentication);
		assertThat(list).isNotNull();
		assertThat(list.getName()).isEqualTo("name");
	}

	@Test
	public void testFindOneByListIdAndUser_ResultOk() throws Exception {
		List list = listService.findOneByListIdAndUser(1, authentication);
		assertThat(list).isNotNull();
		assertThat(list.getName()).isEqualTo("name");
	}

	@Test(expected = RecordNotFoundException.class)
	public void testFindOneByListIdAndUser_NoListFound() throws Exception {
		when(listRepository.findOneByListIdAndUser(any(Integer.class), any(User.class))).thenReturn(Optional.empty());

		listService.findOneByListIdAndUser(1, authentication);
	}

	@Test
	public void testFindAllListsByUser_ResultOk() throws Exception {
		when(listRepository.findAllByUser(any(User.class))).thenReturn(Arrays.asList(list));

		java.util.List<List> lists = listService.findAllListsByUser(authentication);
		assertThat(lists).isNotEmpty();
		assertThat(lists.size()).isEqualTo(1);
	}

	@Test
	public void testRenameList_ResultOk() throws Exception {
		listRequest.setName("updated name");
		List list = listService.renameList(1, listRequest, authentication);
		assertThat(list.getName()).isEqualTo("updated name");
	}

	@Test(expected = RecordNotFoundException.class)
	public void testRenameList_NoListFound() throws Exception {
		when(listRepository.findOneByListIdAndUser(any(Integer.class), any(User.class))).thenReturn(Optional.empty());

		listService.renameList(1, listRequest, authentication);
	}

	@Test
	public void testDeleteList_ResultOk() throws Exception {
		doNothing().when(listRepository).deleteByListIdAndUser(any(Integer.class), any(User.class));

		listService.deleteList(1, authentication);
		verify(listRepository, times(1)).deleteByListIdAndUser(1, user);
	}

}
