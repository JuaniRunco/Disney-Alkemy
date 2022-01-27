package com.example.disney.disney.controller;

import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.dto.BasicDTO.CharacterBasicDTO;
import com.example.disney.disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    //Filters for the characters
    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Long weight,
            @RequestParam(required = false) Set<Long> movies
    ) {
        List<CharacterBasicDTO> characters = this.characterService.getByFilters(name, age, weight, movies);
        return ResponseEntity.ok(characters);
    }

    //Filter by character id
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> findCharacterById(@PathVariable Long id) {
        CharacterDTO character = this.characterService.getCharacterById(id);
        return ResponseEntity.ok().body(character);
    }

    //Create a character and assign it to an existing movie
    @PostMapping
    public ResponseEntity<CharacterDTO> save(@Valid @RequestBody CharacterDTO character, @RequestParam Long idMovie) {
        CharacterDTO characterSaved = this.characterService.save(character, idMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterSaved);
    }

    //Update by character id
    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> update(@PathVariable Long id, @RequestBody CharacterDTO dto) {
        CharacterDTO result = this.characterService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    //Delete by character id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        this.characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
