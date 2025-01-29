package com.agendamais.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sales")
public class sale_model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private product_model product;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private user_model user;

    @ManyToOne
    @JoinColumn(name = "id_store", nullable = false)
    private store_model store;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Informa a quantidade")
    @Positive(message = "A quantidade tem que ser maior de 0")
    private int amount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public product_model getProduct() {
        return product;
    }

    public void setProduct(product_model product) {
        this.product = product;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
