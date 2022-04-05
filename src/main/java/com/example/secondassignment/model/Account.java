package com.example.secondassignment.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_user", updatable = false, unique = true, nullable = false)
    private Integer idUser;

    @NonNull
    @Column(name = "first_name", length = 100)
    private String firstName;

    @NonNull
    @Column(name = "last_name", length = 100)
    private String lastName;

    @NonNull
    @Column(name = "email", unique = true, length = 100)
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;
}
