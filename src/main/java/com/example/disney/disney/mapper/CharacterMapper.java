package com.example.disney.disney.mapper;

import com.example.disney.disney.dto.BasicDTO.CharacterBasicDTO;
import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    public CharacterEntity characterDTO2Entity(CharacterDTO dto) {
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setName(dto.getName());
        characterEntity.setImage(dto.getImage());
        characterEntity.setAge(dto.getAge());
        characterEntity.setWeight(dto.getWeight());
        characterEntity.setHistory(dto.getHistory());

        return characterEntity;
    }

    public CharacterDTO characterEntity2DTO(CharacterEntity entity, boolean loadMovies) {

        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(entity.getId());
        characterDTO.setName(entity.getName());
        characterDTO.setImage(entity.getImage());
        characterDTO.setAge(entity.getAge());
        characterDTO.setWeight(entity.getWeight());
        characterDTO.setHistory(entity.getHistory());

        if (loadMovies) {
            List<MovieDTO> moviesDTO = this.movieMapper.movieEntityList2DTOList(entity.getMovies(), false);
            characterDTO.setMovies(moviesDTO);
        }
        return characterDTO;
    }

    public Set<CharacterEntity> characterDTOList2EntitySet(List<CharacterDTO> dtos) {
        Set<CharacterEntity> entities = new HashSet<>();
        for (CharacterDTO dto : dtos) {
            entities.add(characterDTO2Entity(dto));
        }
        return entities;
    }


    public List<CharacterDTO> characterEntitySet2DTOList(Collection<CharacterEntity> entities, boolean loadMovies) {
        List<CharacterDTO> dtos = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            dtos.add(characterEntity2DTO(entity, loadMovies));
        }
        return dtos;
    }

    public void characterEntityRefreshValues(CharacterEntity entity, CharacterDTO dto){
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setWeight(dto.getWeight());
        entity.setHistory(dto.getHistory());
    }


    public List<CharacterBasicDTO> characterEntityList2BasicDtoList(List<CharacterEntity> entities) {
        List<CharacterBasicDTO> dtos = new ArrayList<>();
        CharacterBasicDTO basicDTO;
        for (CharacterEntity entity : entities) {
            basicDTO = new CharacterBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImage(entity.getImage());
            basicDTO.setName(entity.getName());
            dtos.add(basicDTO);

        }
        return dtos;
    }
}
