package com.example.disney.disney;

import com.example.disney.disney.mapper.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisneyApplication implements CommandLineRunner {

	@Autowired
	MovieRepository movieRepository;
	public static void main(String[] args) {
		SpringApplication.run(DisneyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*		CharacterEntity character=new CharacterEntity();
		character.setName("Iron Man");
		character.setHistory("dksadkaskdjaklsdjkalsdjas");
		character.setWeight(80L);
		character.setAge(45);
		character.setMovies();*/

	}
}
