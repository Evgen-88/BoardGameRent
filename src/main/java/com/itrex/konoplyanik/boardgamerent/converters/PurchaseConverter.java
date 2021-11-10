package com.itrex.konoplyanik.boardgamerent.converters;

import java.util.ArrayList;
import java.util.List;

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
				.quantity(purchase.getQuantity())
				.price(purchase.getPrice())
				.accessoryId(purchase.getAccessoryId())
				.accessoryName(purchase.getAccessory().getName())
				.build();
	}
}
