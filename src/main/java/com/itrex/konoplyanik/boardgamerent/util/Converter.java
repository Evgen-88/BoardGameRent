package com.itrex.konoplyanik.boardgamerent.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderDTO;
import com.itrex.konoplyanik.boardgamerent.dto.OrderListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RoleDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserDTO;
import com.itrex.konoplyanik.boardgamerent.dto.UserListDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Accessory;
import com.itrex.konoplyanik.boardgamerent.entity.BoardGame;
import com.itrex.konoplyanik.boardgamerent.entity.Order;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;
import com.itrex.konoplyanik.boardgamerent.entity.Rent;
import com.itrex.konoplyanik.boardgamerent.entity.Role;
import com.itrex.konoplyanik.boardgamerent.entity.User;

public class Converter {

	public static Accessory convertAccessoryToEntity(AccessoryDTO accessoryDTO) {
		Accessory accessory = new Accessory();
		accessory.setId(accessoryDTO.getId());
		accessory.setName(accessoryDTO.getName());
		accessory.setPrice(accessoryDTO.getPrice());
		accessory.setQuantity(accessoryDTO.getQuantity());
		return accessory;
	}

	public static AccessoryDTO convertAccessoryToDTO(Accessory accessory) {
		return AccessoryDTO.builder()
				.id(accessory.getId())
				.name(accessory.getName())
				.price(accessory.getPrice())
				.quantity(accessory.getQuantity())
				.build();
	}
	
	public static BoardGame convertBoardGameToEntity(BoardGameDTO boardGameDTO) {
		BoardGame boardGame = new BoardGame();
		boardGame.setId(boardGameDTO.getId());
		boardGame.setName(boardGameDTO.getName());
		boardGame.setRentPrice(boardGameDTO.getRentPrice());
		boardGame.setQuantity(boardGameDTO.getQuantity());
		return boardGame;
	}

	public static BoardGameDTO convertBoardGameToDTO(BoardGame boardGame) {
		return BoardGameDTO.builder()
				.id(boardGame.getId())
				.name(boardGame.getName())
				.rentPrice(boardGame.getRentPrice())
				.quantity(boardGame.getQuantity())
				.build();
	}

	public static Order convertOrderToEntity(OrderDTO orderDTO) {
		Order order = new Order();
		order.setId(orderDTO.getId());
		order.setUserId(orderDTO.getId());
		order.setTotalPrice(orderDTO.getTotalPrice());
		order.setDate(orderDTO.getDate());
		order.setStatus(orderDTO.getStatus());
		return order;
	}

	public static OrderDTO convertOrderToDTO(Order order) {
		
		return OrderDTO.builder()
				.id(order.getId())
				.userId(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.purchases(convertPurchasesToDTO(order.getPurchases()))
				.rents(convertRentsToDTO(order.getRents()))
				.build();
	}
	
	public static OrderListDTO convertOrderToListDTO(Order order) {
		return OrderListDTO.builder()
				.userId(order.getUserId())
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.date(order.getDate())
				.status(order.getStatus())
				.build();
	}

	private static List<PurchaseDTO> convertPurchasesToDTO(Set<Purchase> purchases) {
		List<PurchaseDTO> purchasesDTO = new ArrayList<>();
		for (Purchase purchase : purchases) {
			purchasesDTO.add(convertPurchaseToDTO(purchase));
		}
		return purchasesDTO;
	}
	
	private static List<RentDTO> convertRentsToDTO(Set<Rent> rents) {
		List<RentDTO> rentsDTO = new ArrayList<>();
		for (Rent rent : rents) {
			rentsDTO.add(convertRentToDTO(rent));
		}
		return rentsDTO;
	}

	public static Purchase convertPurchaseToEntity(PurchaseDTO purchaseDTO) {
		Purchase purchase = new Purchase();
		purchase.setId(purchaseDTO.getId());
		purchase.setAccessoryId(purchaseDTO.getAccessoryId());
		purchase.setOrderId(purchaseDTO.getOrderId());
		purchase.setQuantity(purchaseDTO.getQuantity());
		purchase.setPrice(purchaseDTO.getPrice());
		return purchase;
	}

	public static PurchaseDTO convertPurchaseToDTO(Purchase purchase) {
		return PurchaseDTO.builder()
				.id(purchase.getId())
				.orderId(purchase.getOrderId())
				.quantity(purchase.getQuantity())
				.price(purchase.getPrice())
				.accessoryId(purchase.getAccessoryId())
				.accessoryName(purchase.getAccessory().getName())
				.build();
	}

	public static Rent convertRentToEntity(RentDTO rentDTO) {
		Rent rent = new Rent();
		rent.setId(rentDTO.getId());
		rent.setBoardGameId(rentDTO.getBoardGameId());
		rent.setOrderId(rentDTO.getOrderId());
		rent.setRentFrom(rentDTO.getRentFrom());
		rent.setRentTo(rentDTO.getRentTo());
		rent.setPrice(rentDTO.getPrice());
		return rent;
	}

	public static RentDTO convertRentToDTO(Rent rent) {
		return RentDTO.builder()
				.id(rent.getId())
				.orderId(rent.getOrderId())
				.rentFrom(rent.getRentFrom())
				.rentTo(rent.getRentTo())
				.price(rent.getPrice())
				.boardGameId(rent.getBoardGameId())
				.boardGameName(rent.getBoardGame().getName())
				.build();
	}
	
	public static Role convertRoleToEntity(RoleDTO roleDTO) {
		Role role = new Role();
		role.setId(roleDTO.getId());
		role.setName(roleDTO.getName());
		return role;
	}
	
	public static RoleDTO convertRoleToDTO(Role role) {
		return RoleDTO.builder()
				.id(role.getId())
				.name(role.getName())
				.build();
	}
	
	public static User convertUserToEntity(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setLogin(userDTO.getLogin());
		user.setPassword(userDTO.getPassword());
		user.setName(userDTO.getName());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		user.setRoles(convertRolesToEntity(userDTO.getRoles()));
		return user;
	}
	
	private static Set<Role> convertRolesToEntity(List<RoleDTO> rolesDTO) {
		Set<Role> roles = new HashSet<>();
		for (RoleDTO roleDTO: rolesDTO) {
			roles.add(convertRoleToEntity(roleDTO));
		}
		return roles;
	}
	
	public static UserDTO convertUserToDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.login(user.getLogin())
				.password(user.getPassword())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.roles(convertRolesToDTO(user.getRoles()))
				.build();
	}
	
	public static UserListDTO convertUserToListDTO(User user) {
		return UserListDTO.builder()
				.id(user.getId())
				.login(user.getLogin())
				.name(user.getName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.roles(convertRolesToDTO(user.getRoles()))
				.build();
	}
	
	private static List<RoleDTO> convertRolesToDTO(Set<Role> roles) {
		List<RoleDTO> rolesDTO = new ArrayList<>();
		for (Role role : roles) {
			rolesDTO.add(convertRoleToDTO(role));
		}
		return rolesDTO;
	}

}
