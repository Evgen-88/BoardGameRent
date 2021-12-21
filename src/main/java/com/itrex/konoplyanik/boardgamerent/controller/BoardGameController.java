package com.itrex.konoplyanik.boardgamerent.controller;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.BoardGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boardgames")
@RequiredArgsConstructor
public class BoardGameController {

	private final BoardGameService boardGameService;

	@GetMapping
	public ResponseEntity<List<BoardGameDTO>> findAll() {
		List<BoardGameDTO> boardGames = null;
		try {
			boardGames = boardGameService.findAll();
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return boardGames != null && !boardGames.isEmpty() ? new ResponseEntity<>(boardGames, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BoardGameDTO> findById(@PathVariable Long id) {
		BoardGameDTO boardGame = null;
		try {
			boardGame = boardGameService.findById(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return boardGame != null ? new ResponseEntity<>(boardGame, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('BOARD_GAME_WRITE_DELETE')")
	public ResponseEntity<BoardGameDTO> add(@RequestBody BoardGameDTO boardGame) {
		BoardGameDTO boardGameDTO = null;
		try {
			boardGameDTO = boardGameService.add(boardGame);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(boardGameDTO, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('BOARD_GAME_WRITE_DELETE')")
	public ResponseEntity<BoardGameDTO> update(@RequestBody BoardGameDTO boardGame) {
		BoardGameDTO boardGameDTO = null;
		try {
			boardGameDTO = boardGameService.update(boardGame);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return boardGameDTO != null ? new ResponseEntity<>(boardGameDTO, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('BOARD_GAME_WRITE_DELETE')")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			boardGameService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
