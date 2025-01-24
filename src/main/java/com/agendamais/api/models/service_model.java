package com.agendamais.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Services")
public class service_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer time;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "idCategories")
    private category_model category;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public category_model getCategory() {
        return category;
    }

    public void setCategory(category_model category) {
        this.category = category;
    }
}
