package com.example.disney.disney.service.impl;

import com.example.disney.disney.Enum.MovieNotFound;
import com.example.disney.disney.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.dto.FiltersDTO.MovieFiltersDTO;
import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.entity.CharacterEntity;
import com.example.disney.disney.entity.MovieEntity;
import com.example.disney.disney.exception.ParamNotFound;
import com.example.disney.disney.mapper.MovieMapper;
import com.example.disney.disney.repository.MovieRepository;
import com.example.disney.disney.repository.specifications.MovieSpecification;
import com.example.disney.disney.service.CharacterService;
import com.example.disney.disney.service.MovieService;
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
    public MovieDTO getMovieById(Long id) {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound(MovieNotFound.IdMovie.getDescription());
        }
        return movieMapper.movieEntity2DTO(entity.get(), true);
    }

    //Para encontrar una movieEntity
    public MovieEntity getEntityById(Long id) {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound(MovieNotFound.IdMovie.getDescription());
        }
        return entity.get();
    }

    //Para filtrar la movie por title, genre y ordenarla por creationDate
    public List<MovieBasicDTO> getByFilters(String title, Long genre, String order) {
        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, genre, order);
        List<MovieEntity> entities = this.movieRepository.findAll(this.movieSpecification.getByFilters(filtersDTO));
        if (entities.isEmpty()){
                throw new ParamNotFound(MovieNotFound.FilterCharacter.getDescription());
         }
        List<MovieBasicDTO> dtos = this.movieMapper.movieEntityList2BasicDTOList(entities);
        return dtos;
    }

    //Para actualizar una movieDTO por su id
    public MovieDTO update(Long id, MovieDTO dto) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound(MovieNotFound.IdMovie.getDescription());
        }
        this.movieMapper.movieEntityRefreshValues(entity.get(), dto);
        MovieEntity entitySaved = this.movieRepository.save(entity.get());
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

    //Para agregar un character a una Movie
    public void addCharacter(Long idMovie, Long idCharacter) {
        MovieEntity movie = getEntityById(idMovie);
        Set<CharacterEntity> entities = movie.getCharacters();
        entities.add(characterService.getEntityById(idCharacter));
        movie.setCharacters(entities);
        movieRepository.save(movie);
    }

    //Para eliminar un character a una Movie
    public void removeCharacter(Long idMovie, Long idCharacter){
        MovieEntity movie= getEntityById(idMovie);
        Set<CharacterEntity> entities=movie.getCharacters();
        entities.remove(characterService.getEntityById(idCharacter));
        movie.setCharacters(entities);
        movieRepository.save(movie);
    }

    //Para borrar la movie
    public void delete(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ParamNotFound(MovieNotFound.IdMovie.getDescription());
        }
        this.movieRepository.deleteById(id);
    }

}
