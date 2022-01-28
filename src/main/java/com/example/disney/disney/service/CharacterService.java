package com.example.disney.disney.service;

import com.example.disney.disney.dto.BasicDTO.CharacterBasicDTO;
import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.entity.CharacterEntity;
import java.util.List;
import java.util.Set;

public interface CharacterService {

    CharacterDTO save (CharacterDTO dto,Long idMovie);

    CharacterDTO getCharacterById(Long id);

    CharacterDTO update(Long id, CharacterDTO dto);

    void delete(Long id);

    List<CharacterBasicDTO> getByFilters(String name, Integer age, Long weight, Set<Long> movies);

    CharacterEntity getEntityById(Long id);

    List<CharacterBasicDTO> getAllCharacterBasic();
}
