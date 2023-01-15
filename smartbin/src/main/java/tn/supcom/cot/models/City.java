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
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private String country;

    @Column
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name +"["
                + "id='" + id +
                ", country=" + country +
                ']';
    }

}
