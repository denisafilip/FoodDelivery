package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "food")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idFood")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_food", updatable = false, unique = true, nullable = false)
    private Integer idFood;

    @NonNull
    @Column(name = "name", length = 100)
    private String name;

    @NonNull
    @Column(name = "price")
    private Integer price;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_category")
    private Category category;

    @NonNull
    //@JsonIgnore
    @JsonBackReference
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "foods")
    private List<Order> orders;


}
