package com.example.disney.disney.dto;

/*import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;*/
import com.example.disney.disney.entity.GenreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {

    private Long id;

    private String image;

    @NotBlank
    private String title;

    private String creationDate;

    @Positive
    @Max(value = 5)
    private Integer qualification;

    private GenreDTO genre;

    private Long genreId;

    private List<CharacterDTO> characters;

    //@ArraySchema(schema=@Schema( accessMode = Schema.AccessMode.READ_WRITE))
    //private List<Long> characterId;

}
