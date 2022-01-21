package com.example.disney.disney.controller;

import com.example.disney.disney.dto.MovieBasicDTO;
import com.example.disney.disney.dto.MovieDTO;
import com.example.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieBasicDTO>> findAll(){
        List<MovieBasicDTO> movies= movieService.getAllMoviesBasic();
        return ResponseEntity.ok().body(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable Long id) throws NotFoundException{
        MovieDTO movie= movieService.getMovieById(id);
        return ResponseEntity.ok().body(movie);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> save(@RequestBody MovieDTO movie){
        MovieDTO movieSaved= this.movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO dto) throws NotFoundException {
        MovieDTO result=this.movieService.update(id,dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NotFoundException {
        if (movieService.getMovieById(id)==null){
            return ResponseEntity.notFound().build();
        } else {
            this.movieService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
