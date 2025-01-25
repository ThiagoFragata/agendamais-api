package com.agendamais.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Employees")
public class employee_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer cpf;

    @Column
    private String photo;

    @OneToOne
    @JoinColumn(name = "idUsers")
    private user_model user;

    @OneToOne
    @JoinColumn(name = "idStores")
    private store_model store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
