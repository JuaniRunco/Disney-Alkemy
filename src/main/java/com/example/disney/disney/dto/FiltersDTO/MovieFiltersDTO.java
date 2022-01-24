package com.example.disney.disney.dto.FiltersDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MovieFiltersDTO {

    private String title;
    private Long genre;
    private String order;

    public MovieFiltersDTO(String title,Long genre, String order) {
        this.title = title;
        this.genre=genre;
        this.order = order;
    }

    public boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    /*public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }*/
}
