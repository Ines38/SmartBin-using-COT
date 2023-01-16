package tn.supcom.cot.models;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import tn.supcom.cot.FieldPropertyVisibilityStrategy;

import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Entity
@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
public class Mesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date date;

    @Column
    private String val;

    @Column
    private Indicator indicator;

    @Override
    public String toString() {
        return "mesure [id=" + id + ", date=" + date + ", val1=" + val
                + ", indicateur=" + indicator + "]";
    }
}
