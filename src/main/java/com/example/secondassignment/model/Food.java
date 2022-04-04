package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_food", updatable = false, unique = true, nullable = false)
    private Integer idFood;

    @NonNull
    @Column(name = "name", unique = true, length = 100)
    private String name;

    @NonNull
    @Column(name = "price")
    private Integer price;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category")
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "foods")
    private List<Order> orders;

    @JsonIgnore
    @ManyToMany(mappedBy = "foods")
    private List<Menu> menus;

}
