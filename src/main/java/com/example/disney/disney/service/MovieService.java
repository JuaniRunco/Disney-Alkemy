package com.example.disney.disney.service;

import com.example.disney.disney.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.entity.CharacterEntity;
import javassist.NotFoundException;

import java.util.List;
import java.util.Set;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    List<MovieBasicDTO> getAllMoviesBasic();

    MovieDTO getMovieById(Long id) throws NotFoundException;

    MovieDTO update(Long id, MovieDTO dto) throws NotFoundException;

    void delete(Long id);

    List<MovieBasicDTO> getByFilters(String title, Long genre,String order);

    void addCharacter(Long idMovie, CharacterEntity entity);

    void addCharacterList(Long idMovie, Set<Long> charactersId);
}
