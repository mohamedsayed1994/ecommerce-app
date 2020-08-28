package com.test.ecommerceapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "state")
@Getter
@Setter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    // TODO: 28-Aug-20 Set up one-to-many with country
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;


}
