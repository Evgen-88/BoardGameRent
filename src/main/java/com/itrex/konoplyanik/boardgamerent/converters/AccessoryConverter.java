package com.itrex.konoplyanik.boardgamerent.converters;

import com.itrex.konoplyanik.boardgamerent.dto.AccessoryDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Accessory;

public class AccessoryConverter {

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
}
