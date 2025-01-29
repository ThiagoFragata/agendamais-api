package com.agendamais.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "Address")
public class address_model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String public_place;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String neighborhood;

    @Column
    private int longitude;

    @Column
    private int latitude;

    @OneToOne(mappedBy = "address")
    @JsonIgnoreProperties("address") // Evita o looping
    private store_model store_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublic_place() {
        return public_place;
    }

    public void setPublic_place(String public_place) {
        this.public_place = public_place;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public @NotNull int getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public @NotNull int getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public store_model getStore_id() {
        return store_id;
    }

    public void setStore_id(store_model store_id) {
        this.store_id = store_id;
    }
}
