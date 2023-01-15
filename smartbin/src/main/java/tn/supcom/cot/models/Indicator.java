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
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String libelle;

    @Column()
    private String unite;

    public Indicator() {
    }

    @Override
    public String toString() {
        return libelle;
    }
}
