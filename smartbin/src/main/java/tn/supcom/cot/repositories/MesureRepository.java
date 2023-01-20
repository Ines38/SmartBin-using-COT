package tn.supcom.cot.repositories;

import jakarta.nosql.mapping.Repository;
import tn.supcom.cot.models.Mesure;

import java.util.stream.Stream;

public interface MesureRepository  extends Repository<Mesure, String> {
    Stream<Mesure> findAll();
}
