package com.itrex.konoplyanik.boardgamerent.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itrex.konoplyanik.boardgamerent.dto.BoardGameDTO;
import com.itrex.konoplyanik.boardgamerent.service.BoardGameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boardGames")
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
	public BoardGameDTO add(@RequestBody BoardGameDTO boardGame) {
		return boardGameService.add(boardGame);
	}
	
	@PutMapping
	public BoardGameDTO update(@RequestBody BoardGameDTO boardGame) {
		return boardGameService.update(boardGame);
	}
	
	@DeleteMapping("{id}")
	public boolean delete(@PathVariable long id) {
	   return boardGameService.delete(id);
	}

}
