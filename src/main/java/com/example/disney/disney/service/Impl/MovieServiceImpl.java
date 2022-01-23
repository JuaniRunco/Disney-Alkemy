package com.example.disney.disney.service.Impl;

import com.example.disney.disney.mapper.repository.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.mapper.repository.dto.FiltersDTO.MovieFiltersDTO;
import com.example.disney.disney.mapper.repository.dto.MovieDTO;
import com.example.disney.disney.entity.MovieEntity;
import com.example.disney.disney.mapper.MovieMapper;
import com.example.disney.disney.mapper.repository.MovieRepository;
import com.example.disney.disney.mapper.repository.specifications.MovieSpecification;
import com.example.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;
    private MovieRepository movieRepository;
    private MovieSpecification movieSpecification;

    @Autowired
    public MovieServiceImpl(
            MovieMapper movieMapper,
            MovieRepository movieRepository,
            MovieSpecification movieSpecification){
        this.movieMapper=movieMapper;
        this.movieRepository=movieRepository;
        this.movieSpecification=movieSpecification;
    }

    public MovieDTO save(MovieDTO dto) {
        MovieEntity entity= this.movieMapper.movieDTO2Entity(dto);
        MovieEntity entitySaved= this.movieRepository.save(entity);
       return this.movieMapper.movieEntity2DTO(entitySaved,true);
    }

    public List<MovieBasicDTO> getAllMoviesBasic() {
        List<MovieEntity> entities = this.movieRepository.findAll();
        List<MovieBasicDTO> result= this.movieMapper.movieEntityList2BasicDTOList(entities);
        return result;
    }

    public MovieDTO getMovieById(Long id) throws NotFoundException {
        MovieEntity entity= movieRepository.findById(id).orElseThrow(()->new NotFoundException("The movie with that id was not found"));
        return movieMapper.movieEntity2DTO(entity,true);
    }


    public MovieDTO update(Long id, MovieDTO dto) throws NotFoundException {
        Optional<MovieEntity> entity=this.movieRepository.findById(id);
        if (!entity.isPresent()){
            throw new NotFoundException("This id was not found ");
        }
        this.movieMapper.movieEntityRefreshValues(entity.get(),dto);
        MovieEntity entitySaved=this.movieRepository.save(entity.get());
        MovieDTO result=this.movieMapper.movieEntity2DTO(entitySaved,true);
        return result;
    }

    public List<MovieDTO> getByFilters(String title, Set<Long> genres,String date ,String order) {
        MovieFiltersDTO filtersDTO= new MovieFiltersDTO(title,genres,date,order);
        List<MovieEntity> entities= this.movieRepository.findAll(this.movieSpecification.getByFilters(filtersDTO));
        List<MovieDTO> dtos= this.movieMapper.movieEntityList2DTOList(entities,true);
        return dtos;
    }


    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }



}
