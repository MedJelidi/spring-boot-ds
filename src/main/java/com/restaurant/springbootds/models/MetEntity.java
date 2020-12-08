package com.restaurant.springbootds.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Met")
public class MetEntity {
    @Id
    private String nom;

    @Column(nullable = false)
    private float prix;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToMany
    @JoinTable(name = "orderedWith",
            joinColumns = @JoinColumn(name = "met"),
            inverseJoinColumns = @JoinColumn(name = "ticket"))
    @JsonIgnore
    private List<TicketEntity> tickets;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
