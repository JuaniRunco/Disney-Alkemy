package com.example.disney.disney.controller;

import com.example.disney.disney.dto.CharacterBasicDTO;
import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.service.CharacterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    //    @Autowired
    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>> findAll() {
        List<CharacterBasicDTO> characters = this.characterService.getAllCharacterBasic();
        //return ResponseEntity.ok(characters);
        return ResponseEntity.ok().body(characters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> findCharacterById(@PathVariable Long id) throws NotFoundException {
        CharacterDTO character = this.characterService.getCharacterById(id);
        return ResponseEntity.ok(character);
        //return ResponseEntity.ok().body(character);
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO character) {
        CharacterDTO characterSaved = this.characterService.save(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> update(@PathVariable Long id, @RequestBody CharacterDTO dto) throws NotFoundException {
        CharacterDTO result=this.characterService.update(id,dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        this.characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
