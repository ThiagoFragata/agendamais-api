package com.agendamais.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Stores")
public class store_model implements Serializable {

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
    @JoinColumn(name = "id_address")
    private address_model address;

    @JsonIgnoreProperties("store")
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<category_model> categories = new ArrayList<>();

    @JsonIgnoreProperties("store")
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<service_model> services = new ArrayList<>();

    @JsonIgnoreProperties("store")
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<employee_model> employees = new ArrayList<>();

    @JsonIgnoreProperties("store")
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<sale_model> sales = new ArrayList<>();

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

    public List<category_model> getCategories() {
        return categories;
    }

    public void setCategories(List<category_model> categories) {
        this.categories = categories;
    }

    public List<employee_model> getEmployees() {
        return employees;
    }

    public void setEmployees(List<employee_model> employees) {
        this.employees = employees;
    }

    public List<service_model> getServices() {
        return services;
    }

    public void setServices(List<service_model> services) {
        this.services = services;
    }

    public List<sale_model> getSales() {
        return sales;
    }

    public void setSales(List<sale_model> sales) {
        this.sales = sales;
    }
}
