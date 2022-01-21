package com.example.disney.disney.service.Impl;

import com.example.disney.disney.dto.CharacterBasicDTO;
import com.example.disney.disney.dto.CharacterDTO;
import com.example.disney.disney.entity.CharacterEntity;
import com.example.disney.disney.entity.MovieEntity;
import com.example.disney.disney.mapper.CharacterMapper;
import com.example.disney.disney.repository.CharacterRepository;
import com.example.disney.disney.service.CharacterService;

import com.example.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterMapper characterMapper;
    @Autowired
    private CharacterRepository characterRepository;

    private MovieService movieService;

   /* @Autowired
    public CharacterServiceImpl(
            CharacterMapper characterMapper,
            CharacterRepository characterRepository) {
        this.characterMapper=characterMapper;
        this.characterRepository=characterRepository;}*/

    public CharacterDTO save(CharacterDTO dto) {
        CharacterEntity entity = characterMapper.characterDTO2Entity(dto);
        CharacterEntity entitySaved = characterRepository.save(entity);
        CharacterDTO result = characterMapper.characterEntity2DTO(entitySaved, false);
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
        CharacterEntity entity = characterRepository.findById(id).orElseThrow(()-> new NotFoundException("The character with that id was not found"));
        return characterMapper.characterEntity2DTO(entity, true);
    }

    public CharacterDTO update(Long id, CharacterDTO dto) throws NotFoundException {
        Optional<CharacterEntity> entity= this.characterRepository.findById(id);
        if (!entity.isPresent()){
            throw new NotFoundException("This id was not found ");
        }
        this.characterMapper.characterEntityRefreshValues(entity.get(),dto);
        CharacterEntity entitySaved= this.characterRepository.save(entity.get());
        CharacterDTO result= this.characterMapper.characterEntity2DTO(entitySaved,false);
        return result;
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
