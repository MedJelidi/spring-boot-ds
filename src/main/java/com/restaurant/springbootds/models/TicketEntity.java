package com.restaurant.springbootds.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Ticket")
@EqualsAndHashCode(exclude = {"table","client","mets"})
@ToString(exclude = {"table","client","mets"})
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;

    @Column(nullable = false)
    private LocalDate date;

    private Integer nbCouvert;

    private float addition;

    @ManyToOne(optional = false)
    private TableEntity table;

    @ManyToOne(optional = false)
    private ClientEntity client;

    @ManyToMany(mappedBy = "tickets", cascade = CascadeType.REMOVE)
    private List<MetEntity> mets;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNbCouvert() {
        return nbCouvert;
    }

    public void setNbCouvert(Integer nbCouvert) {
        this.nbCouvert = nbCouvert;
    }

    public float getAddition() {
        return addition;
    }

    public void setAddition(float addition) {
        this.addition = addition;
    }

    public TableEntity getTable() {
        return table;
    }

    public void setTable(TableEntity table) {
        this.table = table;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public List<MetEntity> getMets() {
        return mets;
    }

    public void setMets(List<MetEntity> mets) {
        this.mets = mets;
    }
}
