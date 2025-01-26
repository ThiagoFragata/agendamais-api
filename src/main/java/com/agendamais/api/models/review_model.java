package com.agendamais.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Reviews")
public class review_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo comentário é obrigatório")
    @Column(nullable = false)
    private String comment;

    @NotNull(message = "O campo estrelas é obrigatório")
    @Min(value = 1, message = "O valor mínimo para estrelas é 1")
    @Max(value = 5, message = "O valor máximo para estrelas é 5")
    @Column(nullable = false)
    private Integer stars;

    @ManyToOne
    @JoinColumn(name = "id_store", nullable = false)
    private store_model store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public store_model getStore() {
        return store;
    }

    public void setStore(store_model store) {
        this.store = store;
    }
}
