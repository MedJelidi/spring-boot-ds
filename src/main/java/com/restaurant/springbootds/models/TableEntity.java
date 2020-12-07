package com.restaurant.springbootds.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name = "RestaurantTable")
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;

    private Integer nbCouvert;

    @Column(nullable = false)
    private String type;

    private float supplement;

    @OneToMany(mappedBy = "table")
    @JsonIgnore
    private List<TicketEntity> tickets;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNbCouvert() {
        return nbCouvert;
    }

    public void setNbCouvert(Integer nbCouvert) {
        this.nbCouvert = nbCouvert;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getSupplement() {
        return supplement;
    }

    public void setSupplement(float supplement) {
        this.supplement = supplement;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }
}
