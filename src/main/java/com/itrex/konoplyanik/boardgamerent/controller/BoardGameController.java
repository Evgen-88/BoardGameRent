package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import com.itrex.konoplyanik.boardgamerent.service.BoardGameService;

import lombok.RequiredArgsConstructor;

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
	@Secured("manager")
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
	@Secured("manager")
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
	@Secured("manager")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			boardGameService.delete(id);
		} catch (ServiceException ex) {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
