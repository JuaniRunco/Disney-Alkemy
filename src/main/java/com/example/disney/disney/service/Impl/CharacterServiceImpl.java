package com.example.disney.disney.service.Impl;

import com.example.disney.disney.dto.BasicDTO.CharacterBasicDTO;
import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.dto.FiltersDTO.CharacterFiltersDTO;
import com.example.disney.disney.entity.CharacterEntity;
import com.example.disney.disney.exception.ParamNotFound;
import com.example.disney.disney.mapper.CharacterMapper;
import com.example.disney.disney.repository.CharacterRepository;
import com.example.disney.disney.repository.specifications.CharacterSpecification;
import com.example.disney.disney.service.CharacterService;

import com.example.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterMapper characterMapper;
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private CharacterSpecification characterSpecification;

    public CharacterDTO save(CharacterDTO dto,Long idMovie) {
        CharacterEntity entity = characterMapper.characterDTO2Entity(dto);
        CharacterEntity entitySaved = characterRepository.save(entity);
        movieService.addCharacter(idMovie,entitySaved.getId());
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
    public CharacterDTO getCharacterById(Long id) {
        Optional<CharacterEntity> entity = characterRepository.findById(id);
        if (!entity.isPresent()){
            throw new ParamNotFound("Character id not found");
        }
        return characterMapper.characterEntity2DTO(entity.get(), true);
    }

    public List<CharacterBasicDTO> getByFilters(String name, Integer age, Long weight, Set<Long> movies) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, movies);
        List<CharacterEntity> entities = this.characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        if (entities.isEmpty()){
            throw new ParamNotFound("No character found with the indicated parameters");
        }
        List<CharacterBasicDTO> dtos = this.characterMapper.characterEntityList2BasicDtoList(entities);
        return dtos;
    }

    public CharacterEntity getEntityById(Long id) {
        if (!characterRepository.existsById(id)){
            throw new ParamNotFound("Character id not found");
        }
        return characterRepository.getById(id);
    }

    public CharacterDTO update(Long id, CharacterDTO dto) {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Character id not found");
        }
        this.characterMapper.characterEntityRefreshValues(entity.get(), dto);
        CharacterEntity entitySaved = this.characterRepository.save(entity.get());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, true);
        return result;
    }

    public void delete(Long id) {
        if (!characterRepository.existsById(id)){
            throw new ParamNotFound("Character id not found");
        }
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
