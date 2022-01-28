package com.example.disney.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
    @NotNull
    private String creationDate;
    @Positive
    @Max(value = 5)
    private Integer qualification;

    private GenreDTO genre;

    private Long genreId;

    private List<CharacterDTO> characters;

}
