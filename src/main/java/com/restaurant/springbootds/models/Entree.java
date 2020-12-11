package com.restaurant.springbootds.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("entree")
public class Entree extends MetEntity {
    public Entree(String nom, float prix) {
        super(nom, prix);
    }

    public Entree() {
        super();
    }
}
