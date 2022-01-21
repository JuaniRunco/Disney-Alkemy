package com.example.disney.disney.service;

import com.example.disney.disney.dto.CharacterBasicDTO;
import com.example.disney.disney.dto.CharacterDTO;
import javassist.NotFoundException;
import java.util.List;

public interface CharacterService {

    CharacterDTO save (CharacterDTO dto);

    List<CharacterBasicDTO> getAllCharacterBasic();

    CharacterDTO getCharacterById(Long id) throws NotFoundException;

    CharacterDTO update(Long id, CharacterDTO dto) throws NotFoundException;

    void delete(Long id);
}
