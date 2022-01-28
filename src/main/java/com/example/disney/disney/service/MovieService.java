package com.example.disney.disney.service;

import com.example.disney.disney.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.dto.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    List<MovieBasicDTO> getAllMoviesBasic();

    MovieDTO getMovieById(Long id);

    MovieDTO update(Long id, MovieDTO dto);

    void delete(Long id);

    List<MovieBasicDTO> getByFilters(String title, Long genre,String order);

    void addCharacter(Long idMovie, Long idCharacter);
    void removeCharacter(Long idMovie, Long idCharacter);

}
