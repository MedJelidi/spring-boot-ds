package com.restaurant.springbootds.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("plat")
public class Plat extends MetEntity {
    public Plat(String nom, float prix) {
        super(nom, prix);
    }

    public Plat() {
        super();
    }
}
