package com.itrex.konoplyanik.boardgamerent.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.dto.AccessoryListDTO;
import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.dto.BoardGameListDTO;
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
		AccessoryDTO accessoryDTO = new AccessoryDTO();
		accessoryDTO.setId(accessory.getId());
		accessoryDTO.setName(accessory.getName());
		accessoryDTO.setPrice(accessory.getPrice());
		accessoryDTO.setQuantity(accessory.getQuantity());
		return accessoryDTO;
	}
	
	public static AccessoryListDTO convertAccessoryToListDTO(Accessory accessory) {
		AccessoryListDTO accessoryListDTO = new AccessoryListDTO();
		accessoryListDTO.setId(accessory.getId());
		accessoryListDTO.setName(accessory.getName());
		return accessoryListDTO;
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
		BoardGameDTO boardGameDTO = new BoardGameDTO();
		boardGameDTO.setId(boardGame.getId());
		boardGameDTO.setName(boardGame.getName());
		boardGameDTO.setRentPrice(boardGame.getRentPrice());
		boardGameDTO.setQuantity(boardGame.getQuantity());
		boardGameDTO.setAvailableQuantity(boardGameDTO.getQuantity() - boardGame.getRents().size());
		return boardGameDTO;
	}
	
	public static BoardGameListDTO convertBoardGameToListDTO(BoardGame boardGame) {
		BoardGameListDTO boardGameListDTO = new BoardGameListDTO();
		boardGameListDTO.setId(boardGame.getId());
		boardGameListDTO.setName(boardGame.getName());
		return boardGameListDTO;
	}

	public static Order convertOrderToEntity(OrderDTO orderDTO) {
		Order order = new Order();
		order.setId(orderDTO.getId());
		order.setUserId(orderDTO.getUser().getId());
		order.setTotalPrice(orderDTO.getTotalPrice());
		order.setDate(orderDTO.getDate());
		order.setStatus(orderDTO.getStatus());
		return order;
	}

	public static OrderDTO convertOrderToDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setTotalPrice(order.getTotalPrice());
		orderDTO.setDate(order.getDate());
		orderDTO.setStatus(order.getStatus());
		orderDTO.setPurchases(convertPurchasesToDTO(order.getPurchases()));
		orderDTO.setRents(convertRentsToDTO(order.getRents()));
		orderDTO.setUser(convertUserToDTO(order.getUser()));
		return orderDTO;
	}
	
	public static OrderListDTO convertOrderToListDTO(Order order) {
		OrderListDTO orderListDTO = new OrderListDTO();
		orderListDTO.setUserId(order.getUserId());
		orderListDTO.setId(order.getId());
		orderListDTO.setTotalPrice(order.getTotalPrice());
		orderListDTO.setDate(order.getDate());
		orderListDTO.setStatus(order.getStatus());
		return orderListDTO;
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
		purchase.setAccessoryId(purchaseDTO.getAccessoryList().getId());
		purchase.setOrderId(purchaseDTO.getOrderId());
		purchase.setQuantity(purchaseDTO.getQuantity());
		purchase.setPrice(purchaseDTO.getPrice());
		return purchase;
	}

	public static PurchaseDTO convertPurchaseToDTO(Purchase purchase) {
		PurchaseDTO purchaseDTO = new PurchaseDTO();
		purchaseDTO.setId(purchase.getId());
		purchaseDTO.setAccessoryList(convertAccessoryToListDTO(purchase.getAccessory()));
		purchaseDTO.setOrderId(purchase.getOrderId());
		purchaseDTO.setQuantity(purchase.getQuantity());
		purchaseDTO.setPrice(purchase.getPrice());
		return purchaseDTO;
	}

	public static Rent convertRentToEntity(RentDTO rentDTO) {
		Rent rent = new Rent();
		rent.setId(rentDTO.getId());
		rent.setBoardGameId(rentDTO.getBoardGameList().getId());
		rent.setOrderId(rentDTO.getOrderId());
		rent.setRentFrom(rentDTO.getRentFrom());
		rent.setRentTo(rentDTO.getRentTo());
		rent.setPrice(rentDTO.getPrice());
		return rent;
	}

	public static RentDTO convertRentToDTO(Rent rent) {
		RentDTO rentDTO = new RentDTO();
		rentDTO.setId(rent.getId());
		rentDTO.setBoardGameList(convertBoardGameToListDTO(rent.getBoardGame()));
		rentDTO.setOrderId(rent.getOrderId());
		rentDTO.setRentFrom(rent.getRentFrom());
		rentDTO.setRentTo(rent.getRentTo());
		rentDTO.setPrice(rent.getPrice());
		return rentDTO;
	}
	
	public static Role convertRoleToEntity(RoleDTO roleDTO) {
		Role role = new Role();
		role.setId(roleDTO.getId());
		role.setName(roleDTO.getName());
		return role;
	}
	
	public static RoleDTO convertRoleToDTO(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(role.getId());
		roleDTO.setName(role.getName());
		return roleDTO;
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
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setLogin(user.getLogin());
		userDTO.setPassword(user.getPassword());
		userDTO.setName(user.getName());
		userDTO.setPhone(user.getPhone());
		userDTO.setEmail(user.getEmail());
		userDTO.setRoles(convertRolesToDTO(user.getRoles()));
		return userDTO;
	}
	
	public static UserListDTO convertUserToListDTO(User user) {
		UserListDTO userListDTO = new UserListDTO();
		userListDTO.setId(user.getId());
		userListDTO.setLogin(user.getLogin());
		userListDTO.setName(user.getName());
		userListDTO.setPhone(user.getPhone());
		userListDTO.setEmail(user.getEmail());
		userListDTO.setRoles(convertRolesToDTO(user.getRoles()));
		return userListDTO;
	}
	
	private static List<RoleDTO> convertRolesToDTO(Set<Role> roles) {
		List<RoleDTO> rolesDTO = new ArrayList<>();
		for (Role role : roles) {
			rolesDTO.add(convertRoleToDTO(role));
		}
		return rolesDTO;
	}

}
