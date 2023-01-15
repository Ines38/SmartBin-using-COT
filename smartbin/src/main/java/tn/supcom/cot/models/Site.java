package tn.supcom.cot.models;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import tn.supcom.cot.FieldPropertyVisibilityStrategy;

import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nomSite;

    @Column
    private double longitude;

    @Column
    private double latitude;

    @Column
    private City city;

    @Override
    public String toString() {
        return nomSite + " ["
                + "longitude=" + longitude
                + "latitude=" + latitude
                + "pays=" + city
                + "]";
    }
}
