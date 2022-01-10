package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.RentDTO;
import com.itrex.konoplyanik.boardgamerent.dto.RentSaveDTO;
import com.itrex.konoplyanik.boardgamerent.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('RENT_READ_WRITE') or hasAuthority('RENT_DELETE')")
public class RentController {

	private final RentService rentService;

	@GetMapping("/{id}")
	public RentDTO findById(@PathVariable Long id) {
		return rentService.findById(id);
	}

	@PostMapping
	public RentDTO add(@RequestBody RentSaveDTO rent) {
		return rentService.add(rent);
	}

	@PutMapping
	public RentDTO update(@RequestBody RentSaveDTO rent) {
		return rentService.update(rent);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('RENT_DELETE')")
	public boolean delete(@PathVariable long id) {
		return rentService.delete(id);
	}

}
