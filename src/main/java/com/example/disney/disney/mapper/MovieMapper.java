package com.example.disney.disney.mapper;

import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.dto.MovieBasicDTO;
import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.entity.CharacterEntity;
import com.example.disney.disney.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

@Component
public class MovieMapper {

    @Autowired
    private CharacterMapper characterMapper;

    public MovieEntity movieDTO2Entity(MovieDTO dto) {
        MovieEntity entity = new MovieEntity();
        // if (dto.getId()!=null) entity.setId(dto.getId());
        entity.setImage(dto.getImage());
        entity.setTitle(dto.getTitle());
        entity.setCreationDate(this.string2LocalDate(dto.getCreationDate()));
        entity.setQualification(dto.getQualification());
        entity.setGenreId(dto.getGenreId());

        Set<CharacterEntity> characters = characterMapper.characterDTOList2EntitySet(dto.getCharacters());
        entity.setCharacters(characters);
        return entity;
    }

    //
    public MovieDTO movieEntity2DTO(MovieEntity entity, boolean loadCharacters) {

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(entity.getId());
        movieDTO.setImage(entity.getImage());
        movieDTO.setTitle(entity.getTitle());
        movieDTO.setCreationDate(entity.getCreationDate().toString());
        movieDTO.setQualification(entity.getQualification());
        movieDTO.setGenreId(entity.getGenreId());

        if (loadCharacters) {
            List<CharacterDTO> characterDTOS = characterMapper.characterEntitySet2DTOList(entity.getCharacters(), false);
            movieDTO.setCharacters(characterDTOS);
        }

        return movieDTO;
    }

    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entities, boolean loadCharacters) {
        List<MovieDTO> dtos = new ArrayList<>();
        for (MovieEntity entity : entities) {
            dtos.add(movieEntity2DTO(entity, loadCharacters));
        }
        return dtos;
    }

    public List<MovieBasicDTO> movieEntityList2BasicDTOList(List<MovieEntity> entities){
        List<MovieBasicDTO> dtos=new ArrayList<>();
        MovieBasicDTO basicDTO;
        for (MovieEntity entity: entities) {
            basicDTO= new MovieBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setCreationDate(entity.getCreationDate().toString());
            basicDTO.setImage(entity.getImage());
            basicDTO.setTitle(entity.getTitle());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public void movieEntityRefreshValues(MovieEntity entity, MovieDTO dto){
        entity.setImage(dto.getImage());
        entity.setTitle(dto.getTitle());
        entity.setCreationDate(this.string2LocalDate(dto.getCreationDate()));
        entity.setQualification(dto.getQualification());
        entity.setGenreId(dto.getGenreId());
        //Characters
        Set<CharacterEntity> charactersEntitieSet = characterMapper.characterDTOList2EntitySet(dto.getCharacters());
        for (CharacterEntity characterEntity: charactersEntitieSet) {
            entity.getCharacters().add(characterEntity);
        }
    }

    public List<MovieEntity> movieDtoList2EntityList(List<MovieDTO> dtos) {
        List<MovieEntity> entities = new ArrayList<>();
        for (MovieDTO dto : dtos) {
            entities.add(this.movieDTO2Entity(dto));
        }
        return entities;
    }

    private LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);

        return date;
    }

}
