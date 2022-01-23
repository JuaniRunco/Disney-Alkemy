package com.example.disney.disney.mapper.repository.dto.FiltersDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MovieFiltersDTO {

    private String title;
    private Set<Long> genres;
    private String date;
    private String order;

    public MovieFiltersDTO(String title, Set<Long> genres,String date, String order) {
        this.title = title;
        this.genres = genres;
        this.date=date;
        this.order = order;
    }

    public boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}
