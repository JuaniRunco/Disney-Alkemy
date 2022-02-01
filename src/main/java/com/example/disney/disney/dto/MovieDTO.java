package com.example.disney.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {

    private Long id;
    @NotBlank
    private String image;
    @NotBlank
    private String title;
    //TODO:agregar comprobacion
    @NotBlank(message = "It cannot be null or empty, remember to enter date format 'yyyy-MM-dd'")
    private String creationDate;
    @Positive
    @Max(value = 5)
    private Integer qualification;

    private GenreDTO genre;

    private Long genreId;

    private List<CharacterDTO> characters;

}
