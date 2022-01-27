package com.example.disney.disney.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
@Getter
@Setter
@SQLDelete(sql = "UPDATE movie SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@RequiredArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String image;

    private String title;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    private Integer qualification; //del 1 al 5

    private boolean deleted = Boolean.FALSE;

    //para obtener toda la info, la cual va a traer un objeto del tipo Genero
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private GenreEntity genre;

    //para guardar y actualizar una pelicula donde realmente tengo el id en una columna
    @Column(name = "genre_id", nullable = false)
    private Long genreId;

    //@ToString.Exclude
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "character_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<CharacterEntity> characters = new HashSet<>();

    public void addCharacter(CharacterEntity entity) {
        this.characters.add(entity);
    }

    public void removeCharacter(CharacterEntity entity) {
        this.characters.remove(entity);
    }

    /*@Override
    public int hashCode() {
        return Objects.hash(id);
    }*/

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MovieEntity other = (MovieEntity) obj;
        return other.id == this.id;
    }

}
