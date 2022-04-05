package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_menu", updatable = false, unique = true, nullable = false)
    private Integer idMenu;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_menu",
            joinColumns = @JoinColumn(name = "id_menu"),
            inverseJoinColumns = @JoinColumn(name = "id_food"))
    private List<Food> foods;

}
