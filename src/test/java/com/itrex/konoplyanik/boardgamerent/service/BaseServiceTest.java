package com.itrex.konoplyanik.boardgamerent.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.Status;
import com.itrex.konoplyanik.boardgamerent.entity.User;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseServiceTest {

	public List<User> getUsers() {
		return new ArrayList<>() {{
			add(User.builder().id(1L).login("ivan").password("ivan").name("Иван").phone(1111111).email("ivan@mail").build());
			add(User.builder().id(2L).login("alex").password("alex").name("Алекс").phone(2222222).email("alex@mail").build());
			add(User.builder().id(3L).login("kir").password("kir").name("Кирилл").phone(3333333).email("kir@mail").build());
			add(User.builder().id(4L).login("piter").password("piter").name("Петр").phone(4444444).email("piter@mail").build());
			add(User.builder().id(5L).login("vova").password("vova").name("Владимир").phone(5555555).email("vova@mail").build());
		}};
	}
	
	public List<Order> getOrders() {
		return new ArrayList<>() {{
			add(Order.builder().id(1L).totalPrice(45).date(LocalDate.of(2021, 10, 23)).status(Status.confirmed).user(getUsers().get(0)).build());
			add(Order.builder().id(2L).totalPrice(90).date(LocalDate.of(2021, 10, 23)).status(Status.confirmed).user(getUsers().get(3)).build());
			add(Order.builder().id(3L).totalPrice(126).date(LocalDate.of(2021, 10, 23)).status(Status.confirmed).user(getUsers().get(1)).build());
			add(Order.builder().id(4L).totalPrice(120).date(LocalDate.of(2021, 10, 22)).status(Status.booked).user(getUsers().get(3)).build());
		}};
	}
	
	public List<Role> getRoles() {
		return new ArrayList<>() {{
			add(Role.builder().id(1L).name("admin").build());
			add(Role.builder().id(2L).name("manager").build());
			add(Role.builder().id(3L).name("customer").build());
		}};
	}
	
	public List<Purchase> getPurchases() {
		return new ArrayList<>() {{
			add(Purchase.builder().id(1L).quantity(2).price(26).accessory(getAccessories().get(1)).order(getOrders().get(2)).build());
			add(Purchase.builder().id(2L).quantity(3).price(36).accessory(getAccessories().get(0)).order(getOrders().get(1)).build());
		}};
	}
	
	public List<Rent> getRents() {
		return new ArrayList<>() {{
			add(Rent.builder().id(1L).rentFrom(LocalDate.of(2021, 10, 23)).rentTo(LocalDate.of(2021, 10, 24)).price(45).boardGame(getBoardGames().get(0)).order(getOrders().get(0)).build());
			add(Rent.builder().id(2L).rentFrom(LocalDate.of(2021, 10, 23)).rentTo(LocalDate.of(2021, 10, 25)).price(90).boardGame(getBoardGames().get(0)).order(getOrders().get(1)).build());
			add(Rent.builder().id(3L).rentFrom(LocalDate.of(2021, 10, 22)).rentTo(LocalDate.of(2021, 10, 24)).price(100).boardGame(getBoardGames().get(2)).order(getOrders().get(2)).build());
			add(Rent.builder().id(4L).rentFrom(LocalDate.of(2021, 10, 25)).rentTo(LocalDate.of(2021, 10, 28)).price(120).boardGame(getBoardGames().get(1)).order(getOrders().get(3)).build());
		}};
	}
	
	public List<Accessory> getAccessories() {
		return new ArrayList<>() {{
			add(Accessory.builder().id(1L).name("Протекторы для карт 65х87").price(12).quantity(24).build());
			add(Accessory.builder().id(2L).name("Протекторы для карт 48х64").price(13).quantity(20).build());
			add(Accessory.builder().id(3L).name("Протекторы для карт 102х143").price(18).quantity(11).build());
		}};
	}
	
	public List<BoardGame> getBoardGames() {
		return new ArrayList<>() {{
			add(BoardGame.builder().id(1L).name("Сквозь века").rentPrice(45).quantity(3).build());
			add(BoardGame.builder().id(2L).name("Сумеречная борьба").rentPrice(40).quantity(2).build());
			add(BoardGame.builder().id(3L).name("Звездные Войны. Восстание").rentPrice(50).quantity(2).build());
			add(BoardGame.builder().id(4L).name("Особняки безумия").rentPrice(50).quantity(4).build());
		}};
	}
}
