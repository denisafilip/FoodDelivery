package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Class for food categories, which divide the menu of a restaurant.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    /**
     * Identifier of the food category in the database table.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_category", updatable = false, unique = true, nullable = false)
    private Integer idCategory;

    /**
     * Name of the food category.
     */
    @NonNull
    @Column(name = "name", length = 100)
    private String name;

    /**
     * Foods that belong to a food category.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Food> foods;

}

