package com.restaurant.springbootds.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Met")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",
        discriminatorType = DiscriminatorType.STRING)
public class MetEntity {
    @Id
    private String nom;

    @Column(nullable = false)
    private float prix;

    @ManyToMany
    @JoinTable(name = "orderedWith",
            joinColumns = @JoinColumn(name = "met"),
            inverseJoinColumns = @JoinColumn(name = "ticket"))
    @JsonIgnore
    private List<TicketEntity> tickets;

    @Column(insertable = false, updatable = false)
    private String type;

    public MetEntity(String nom, float prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public MetEntity() {}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
