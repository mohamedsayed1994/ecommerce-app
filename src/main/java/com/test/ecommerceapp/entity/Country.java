package com.test.ecommerceapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "country")
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;

    // TODO: 28-Aug-20 Set up one-to-many with states
    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> states;
}
