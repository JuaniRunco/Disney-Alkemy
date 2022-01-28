package com.example.disney.disney.controller;

import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.dto.BasicDTO.MovieBasicDTO;
import com.example.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<MovieDTO> save(@Valid @RequestBody MovieDTO movie){
        MovieDTO movieSaved= this.movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSaved);
    }

    //Update by movie id
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO dto) {
        MovieDTO result=this.movieService.update(id,dto);
        return ResponseEntity.ok().body(result);
    }

    //Delete by movie id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (movieService.getMovieById(id)==null){
            return ResponseEntity.notFound().build();
        } else {
            this.movieService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    //Add character to movie
    @PostMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> addCharacterToMovie(@PathVariable Long idMovie, @PathVariable Long idCharacter) {
        movieService.addCharacter(idMovie,idCharacter);
        return ResponseEntity.ok().body(movieService.getMovieById(idMovie));
    }

    //Remove character to movie
    @DeleteMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> removeCharacterToMovie(@PathVariable Long idMovie, @PathVariable Long idCharacter){
        movieService.removeCharacter(idMovie,idCharacter);
        return ResponseEntity.ok().body(movieService.getMovieById(idMovie));
    }
}
