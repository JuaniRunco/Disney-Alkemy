package com.example.disney.disney.dto;

//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CharacterDTO {

    private Long id;

    private String image;

    @NotBlank
    private String name;

    private Integer age;

    private Long weight;

    private String history;

    private List<MovieDTO> movies;


}
