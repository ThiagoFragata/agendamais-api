package com.agendamais.api.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Categories")
public class category_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "idStores")
    private store_model store;

    @OneToMany(mappedBy = "category")
    private List<service_model> services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public store_model getStore() {
        return store;
    }

    public void setStore(store_model store) {
        this.store = store;
    }

    public List<service_model> getServices() {
        return services;
    }

    public void setServices(List<service_model> services) {
        this.services = services;
    }
}
