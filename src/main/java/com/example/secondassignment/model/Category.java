package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_category", updatable = false, unique = true, nullable = false)
    private Integer idCategory;

    @NonNull
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Food> foods;

}

