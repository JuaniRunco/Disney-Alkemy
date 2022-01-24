package com.example.disney.disney.controller;

import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    //Filters for the movie
    @GetMapping
    public ResponseEntity<List<MovieBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false, defaultValue = "ASC") String order
            ){
        List<MovieBasicDTO> movies= this.movieService.getByFilters(title,genre,order);
        return ResponseEntity.ok(movies);
    }

    //Filter by movie id
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable Long id) throws NotFoundException{
        MovieDTO movie= movieService.getMovieById(id);
        return ResponseEntity.ok().body(movie);
    }

    //Create
    @PostMapping
    public ResponseEntity<MovieDTO> save(@RequestBody MovieDTO movie){
        MovieDTO movieSaved= this.movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSaved);
    }

    //Update by movie id
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO dto) throws NotFoundException {
        MovieDTO result=this.movieService.update(id,dto);
        return ResponseEntity.ok().body(result);
    }

    //Crear movie con un personaje y asignarle otro ya creado
    @PostMapping("/withcharacters")
    public ResponseEntity<MovieDTO> saveWithExistentCharacters(
            @RequestParam(required = false)Set<Long> charactersId,
            @RequestBody MovieDTO movieDTO
    ) {
        MovieDTO movie = movieService.save(movieDTO);
        movieService.addCharacterList(movie.getId(), charactersId);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    //Delete by movie id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NotFoundException {
        if (movieService.getMovieById(id)==null){
            return ResponseEntity.notFound().build();
        } else {
            this.movieService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    //TODO: Si hay tiempo agregar Post de pelicula con endpoint /movies/id/character/idCharacter

}
