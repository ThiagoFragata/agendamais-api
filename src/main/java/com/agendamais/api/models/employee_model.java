package com.agendamais.api.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Employees")
public class employee_model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column
    private String photo;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToOne
    @JoinColumn(name = "id_user")
    private user_model user;

    @ManyToOne
    @JoinColumn(name = "id_store", nullable = false)
    private store_model store;

    @ManyToMany(mappedBy = "employees")
    private List<service_model> services = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public user_model getUser() {
        return user;
    }

    public void setUser(user_model user) {
        this.user = user;
    }

    public store_model getStore() {
        return store;
    }

    public void setStore(store_model store) {
        this.store = store;
    }
}
