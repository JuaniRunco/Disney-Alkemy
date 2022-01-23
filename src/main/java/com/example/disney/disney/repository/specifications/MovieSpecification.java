package com.example.disney.disney.repository.specifications;

import com.example.disney.disney.dto.FiltersDTO.MovieFiltersDTO;
import com.example.disney.disney.entity.GenreEntity;
import com.example.disney.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Component
public class MovieSpecification {

    public Specification<MovieEntity> getByFilters(MovieFiltersDTO filtersDTO){
        return (root,query,criteriaBuilder)->{

            List<Predicate> predicates=new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getTitle())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filtersDTO.getTitle().toLowerCase()+"%"
                        )
                );
            }

            if (!CollectionUtils.isEmpty(filtersDTO.getGenres())){
                Join<GenreEntity,MovieEntity> join=root.join("genre",JoinType.INNER);
                Expression<String> genresID=join.get("id");
                predicates.add(genresID.in(filtersDTO.getGenres()));
            }

            if (StringUtils.hasLength(filtersDTO.getDate())){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date= LocalDate.parse(filtersDTO.getDate(),formatter);

                predicates.add(
                        criteriaBuilder.equal(root.<LocalDate>get("creationDate"),date)
                );
            }

            //Remove duplicates
            query.distinct(true);

            String orderByField="creationDate";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)):
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
