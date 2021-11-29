package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Accessory;

public class AccessoryConverter {

	public static Accessory convertAccessoryToEntity(AccessoryDTO accessoryDTO) {
		return Accessory.builder()
				.id(accessoryDTO.getId())
				.name(accessoryDTO.getName())
				.price(accessoryDTO.getPrice())
				.quantity(accessoryDTO.getQuantity())
				.build();
	}

	public static AccessoryDTO convertAccessoryToDTO(Accessory accessory) {
		return AccessoryDTO.builder()
				.id(accessory.getId())
				.name(accessory.getName())
				.price(accessory.getPrice())
				.quantity(accessory.getQuantity())
				.build();
	}
	
}
