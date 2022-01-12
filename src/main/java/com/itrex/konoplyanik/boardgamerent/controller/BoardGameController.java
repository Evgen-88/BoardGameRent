package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.service.BoardGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boardgames")
@RequiredArgsConstructor
public class BoardGameController {

	private final BoardGameService boardGameService;

	@GetMapping
	public List<BoardGameDTO> findAll() {
		return boardGameService.findAll();
	}

	@GetMapping("/{id}")
	public BoardGameDTO findById(@PathVariable Long id) {
		return boardGameService.findById(id);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('BOARD_GAME_WRITE_DELETE')")
	public BoardGameDTO add(@RequestBody BoardGameDTO boardGame) {
		return boardGameService.add(boardGame);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('BOARD_GAME_WRITE_DELETE')")
	public BoardGameDTO update(@RequestBody BoardGameDTO boardGame) {
		return boardGameService.update(boardGame);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('BOARD_GAME_WRITE_DELETE')")
	public boolean delete(@PathVariable long id) {
		return boardGameService.delete(id);
	}

}
