package com.example.disney.disney.service.Impl;

import com.example.disney.disney.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.dto.FiltersDTO.MovieFiltersDTO;
import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.entity.CharacterEntity;
import com.example.disney.disney.entity.MovieEntity;
import com.example.disney.disney.mapper.MovieMapper;
import com.example.disney.disney.repository.MovieRepository;
import com.example.disney.disney.repository.specifications.MovieSpecification;
import com.example.disney.disney.service.CharacterService;
import com.example.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieSpecification movieSpecification;
    @Autowired
    private CharacterService characterService;

    public MovieDTO save(MovieDTO dto) {
        MovieEntity entity = this.movieMapper.movieDTO2Entity(dto);
        MovieEntity entitySaved = this.movieRepository.save(entity);
        return this.movieMapper.movieEntity2DTO(entitySaved, true);
    }

    //Trae los datos basicos de /movies
    public List<MovieBasicDTO> getAllMoviesBasic() {
        List<MovieEntity> entities = this.movieRepository.findAll();
        List<MovieBasicDTO> result = this.movieMapper.movieEntityList2BasicDTOList(entities);
        return result;
    }

    //Para encontrar una movie con /movies/id
    public MovieDTO getMovieById(Long id) throws NotFoundException {
        MovieEntity entity = movieRepository.findById(id).orElseThrow(() -> new NotFoundException("The movie with that id was not found"));
        return movieMapper.movieEntity2DTO(entity, true);
    }

    //Para encontrar una movieEntity
    public MovieEntity getEntityById(Long id) {
        MovieEntity entity = movieRepository.getById(id);
        return entity;
    }

    //Para filtrar la movie por title, genre y ordenarla por creationDate
    public List<MovieBasicDTO> getByFilters(String title, Long genre, String order) {
        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, genre, order);
        List<MovieEntity> entities = this.movieRepository.findAll(this.movieSpecification.getByFilters(filtersDTO));
        List<MovieBasicDTO> dtos = this.movieMapper.movieEntityList2BasicDTOList(entities);
        return dtos;
    }

    //Para actualizar una movieDTO por su id
    public MovieDTO update(Long id, MovieDTO dto) throws NotFoundException {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new NotFoundException("This id was not found ");
        }
        this.movieMapper.movieEntityRefreshValues(entity.get(), dto);
        MovieEntity entitySaved = this.movieRepository.save(entity.get());
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

    //Para agregar un character a una Movie
    public void addCharacter(Long idMovie, CharacterEntity entity) {
        MovieEntity movie = getEntityById(idMovie);
        Set<CharacterEntity> entities = movie.getCharacters();
        entities.add(entity);
        movie.setCharacters(entities);
        movieRepository.save(movie);
    }

    //Para agregar una lista de characters a una movie
    public void addCharacterList(Long idMovie, Set<Long> charactersId) {
        MovieEntity entity = getEntityById(idMovie);
        Set<CharacterEntity> movieCharacters = entity.getCharacters();
        for (Long id : charactersId) {
            movieCharacters.add(characterService.getEntityById(id));
        }
        entity.setCharacters(movieCharacters);
        movieRepository.save(entity);
    }

    //Para borrar la movie
    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }


}
