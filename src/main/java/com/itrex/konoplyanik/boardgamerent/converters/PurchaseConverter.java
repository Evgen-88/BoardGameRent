package com.itrex.konoplyanik.boardgamerent.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.itrex.konoplyanik.boardgamerent.dto.PurchaseDTO;
import com.itrex.konoplyanik.boardgamerent.dto.PurchaseSaveDTO;
import com.itrex.konoplyanik.boardgamerent.entity.Purchase;

public class PurchaseConverter {

	public static List<PurchaseDTO> convertPurchasesToDTO(List<Purchase> purchases) {
		List<PurchaseDTO> purchasesDTO = new ArrayList<>();
		for (Purchase purchase : purchases) {
			purchasesDTO.add(convertPurchaseToDTO(purchase));
		}
		return purchasesDTO;
	}
	
	public static Purchase convertPurchaseToEntity(PurchaseSaveDTO purchaseDTO) {
		return Purchase.builder()
				.id(purchaseDTO.getId())
				.accessory(purchaseDTO.getAccessory())
				.order(purchaseDTO.getOrder())
				.quantity(purchaseDTO.getQuantity())
				.price(purchaseDTO.getPrice())
				.build();
	}

	public static PurchaseDTO convertPurchaseToDTO(Purchase purchase) {
		return PurchaseDTO.builder()
				.id(purchase.getId())
				.quantity(purchase.getQuantity())
				.price(purchase.getPrice())
				.accessoryId(purchase.getAccessory().getId())
				.accessoryName(purchase.getAccessory().getName())
				.build();
	}
	
	public static List<PurchaseDTO> convertSetPurchasesToDTO(Set<Purchase> purchases) {
		List<PurchaseDTO> purchasesDTO = new ArrayList<>();
		for (Purchase purchase : purchases) {
			purchasesDTO.add(convertPurchaseToDTO(purchase));
		}
		return purchasesDTO;
	}

}
