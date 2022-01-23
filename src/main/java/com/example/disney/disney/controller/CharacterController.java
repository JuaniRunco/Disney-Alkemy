package com.example.disney.disney.controller;

import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.dto.BasicDTO.CharacterBasicDTO;
import com.example.disney.disney.service.CharacterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    //    @Autowired
    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

   /* @GetMapping("/all")
    public ResponseEntity<List<CharacterBasicDTO>> findAll() {
        List<CharacterBasicDTO> characters = this.characterService.getAllCharacterBasic();
        return ResponseEntity.ok().body(characters);
    }*/

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

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> findCharacterById(@PathVariable Long id) throws NotFoundException {
        CharacterDTO character = this.characterService.getCharacterById(id);
        return ResponseEntity.ok().body(character);
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO character) {
        CharacterDTO characterSaved = this.characterService.save(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> update(@PathVariable Long id, @RequestBody CharacterDTO dto) throws NotFoundException {
        CharacterDTO result = this.characterService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        this.characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
