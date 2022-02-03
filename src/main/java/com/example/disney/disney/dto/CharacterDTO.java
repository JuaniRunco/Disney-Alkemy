package com.example.disney.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO {

    private Long id;
    @NotBlank
    private String image;
    @NotBlank
    private String name;
    @Positive
    private Integer age;
    @Positive
    private Long weight;
    @Size(max = 255, message = "The story must be maximum 255 characters, including spaces")
    private String history;

    private List<MovieDTO> movies;

}
