package tn.supcom.cot.models;


import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import tn.supcom.cot.FieldPropertyVisibilityStrategy;

import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

@Entity
@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String imei;

    @Column
    private String reference;

    @Column
    private Date dateInstallation; // Date d'installation sur le dernier site

    @Column
    private Date derniereConnection; // Date de dernière connection à l'application'

    @Column
    private Site site;

    @Column
    private List<Mesure> mesures;

    public Box(String imei, String reference, Date derniereConnection, List<Mesure> mesures) {
        this.imei = imei;
        this.reference = reference;
        this.derniereConnection = derniereConnection;
        this.mesures = mesures;
    }

    @Override
    public String toString() {
        return reference + " ["
                + ", imei=" + imei
                + ", installation=" + ( dateInstallation != null ? dateInstallation : null )
                + ", Dernière connexion=" + ( derniereConnection != null ? derniereConnection : null )
              //  + ", site=" + ( site != null ? site.getNomSite() : null )
                + "]";
    }
}
