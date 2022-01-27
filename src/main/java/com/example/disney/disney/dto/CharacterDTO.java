package com.example.disney.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
