package com.example.disney.disney.service;

import com.example.disney.disney.mapper.repository.dto.GenreDTO;

import java.util.List;

public interface GenreService {

    GenreDTO save(GenreDTO dto);

    List<GenreDTO> getAllGenres();
}
