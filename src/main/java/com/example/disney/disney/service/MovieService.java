package com.example.disney.disney.service;

import com.example.disney.disney.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.dto.MovieDTO;
import javassist.NotFoundException;

import java.util.List;
import java.util.Set;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    List<MovieBasicDTO> getAllMoviesBasic();

    MovieDTO getMovieById(Long id) throws NotFoundException;

    MovieDTO update(Long id, MovieDTO dto) throws NotFoundException;

    void delete(Long id);

    List<MovieDTO> getByFilters(String title, Set<Long> genres,String date,String order);
}
