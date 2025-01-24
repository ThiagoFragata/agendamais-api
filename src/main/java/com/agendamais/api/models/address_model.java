package com.agendamais.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Address")
public class address_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String publicPlace;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String neighborhood;

    @Column
    private Integer longitude;

    @Column
    private Integer latitude;
}
