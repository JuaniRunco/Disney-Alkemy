package com.example.disney.disney.service;

import com.example.disney.disney.dto.MovieBasicDTO;
import com.example.disney.disney.dto.MovieDTO;
import javassist.NotFoundException;

import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    List<MovieBasicDTO> getAllMoviesBasic();

    MovieDTO getMovieById(Long id) throws NotFoundException;

    MovieDTO update(Long id, MovieDTO dto) throws NotFoundException;

    void delete(Long id);

}
