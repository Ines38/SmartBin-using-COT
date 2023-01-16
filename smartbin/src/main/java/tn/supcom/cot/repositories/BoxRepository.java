package tn.supcom.cot.repositories;

import tn.supcom.cot.models.Box;
import tn.supcom.cot.models.User;

import java.util.List;

public interface BoxRepository {

    Box saveBox(Box box);

    List<Box> findAll();

    void delete(User userConnected, long id);

    Box findById(Long id);

    Box findByImei(String imei);

}
