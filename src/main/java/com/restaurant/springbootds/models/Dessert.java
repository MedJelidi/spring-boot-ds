package com.restaurant.springbootds.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("dessert")
public class Dessert extends MetEntity {
    public Dessert(String nom, float prix) {
        super(nom, prix);
    }

    public Dessert() {
        super();
    }
}
