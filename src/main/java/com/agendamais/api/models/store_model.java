package com.agendamais.api.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Stores")
public class store_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String about;

    @Column(unique = true)
    private Integer phone;

    @OneToOne
    @JoinColumn(name = "idAddress")
    private address_model address;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public address_model getAddress() {
        return address;
    }

    public void setAddress(address_model address) {
        this.address = address;
    }

    public category_model getCategory() {
        return category;
    }

    public void setCategory(category_model category) {
        this.category = category;
    }
}
