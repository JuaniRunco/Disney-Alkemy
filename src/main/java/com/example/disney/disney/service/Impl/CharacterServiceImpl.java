package com.example.disney.disney.service.Impl;

import com.example.disney.disney.dto.BasicDTO.CharacterBasicDTO;
import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.dto.FiltersDTO.CharacterFiltersDTO;
import com.example.disney.disney.entity.CharacterEntity;
import com.example.disney.disney.mapper.CharacterMapper;
import com.example.disney.disney.repository.CharacterRepository;
import com.example.disney.disney.repository.specifications.CharacterSpecification;
import com.example.disney.disney.service.CharacterService;

import com.example.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CharacterServiceImpl implements CharacterService {


    private CharacterMapper characterMapper;
    private CharacterRepository characterRepository;
    private MovieService movieService;
    private CharacterSpecification characterSpecification;

    @Autowired
    public CharacterServiceImpl(
            CharacterMapper characterMapper,
            CharacterRepository characterRepository,
            MovieService movieService,
            CharacterSpecification characterSpecification) {
        this.characterMapper = characterMapper;
        this.characterRepository = characterRepository;
        this.movieService= movieService;
        this.characterSpecification=characterSpecification;
    }

    public CharacterDTO save(CharacterDTO dto) {
        CharacterEntity entity = characterMapper.characterDTO2Entity(dto);
        CharacterEntity entitySaved = characterRepository.save(entity);
        CharacterDTO result = characterMapper.characterEntity2DTO(entitySaved, true);
        return result;
    }

    //Trae los datos basicos de /characters
    public List<CharacterBasicDTO> getAllCharacterBasic() {
        List<CharacterEntity> entities = characterRepository.findAll();
        List<CharacterBasicDTO> result = characterMapper.characterEntityList2BasicDtoList(entities);
        return result;
    }

    //Para encontrar un solo personaje con /characters/id
    public CharacterDTO getCharacterById(Long id) throws NotFoundException {
        CharacterEntity entity = characterRepository.findById(id).orElseThrow(() -> new NotFoundException("The character with that id was not found"));
        return characterMapper.characterEntity2DTO(entity, true);
    }

    public CharacterDTO update(Long id, CharacterDTO dto) throws NotFoundException {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if (!entity.isPresent()) {
            throw new NotFoundException("This id was not found ");
        }
        this.characterMapper.characterEntityRefreshValues(entity.get(), dto);
        CharacterEntity entitySaved = this.characterRepository.save(entity.get());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, true);
        return result;
    }

    public List<CharacterBasicDTO> getByFilters(String name, Integer age, Long weight, Set<Long> movies) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, movies);
        List<CharacterEntity> entities = this.characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        List<CharacterBasicDTO> dtos = this.characterMapper.characterEntityList2BasicDtoList(entities);
        return dtos;
    }

    public void delete(Long id) {
        this.characterRepository.deleteById(id);
    }



    /*public void addMovie (Long id, Long idMovie){
        CharacterEntity entity = this.characterRepository.getById(id);
        entity.getMovies().size();
        MovieEntity movieEntity= this.movieService.getEntityById(idMovie);
        entity.addMovie(movieEntity);
        this.characterRepository.save(entity);
    }*/

    /*public void removeMovie (Long id, Long idMovie){
        CharacterEntity entity = this.characterRepository.getById(id);
        entity.getMovies().size();
        MovieEntity movieEntity= this.movieService.getEntityById(idMovie);
        entity.removeMovie(movieEntity);
        this.characterRepository.save(entity);
    }*/
}
