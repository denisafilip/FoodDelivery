package com.example.secondassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_status", updatable = false, unique = true, nullable = false)
    private Integer idStatus;

    @NonNull
    @Column(name = "name", length = 100)
    private String name;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "orderStatus", orphanRemoval = true)
    private List<Order> orders;

}