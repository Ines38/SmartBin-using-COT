package tn.supcom.cot.repositories;

import tn.supcom.cot.models.Site;

import java.util.List;

public interface SiteRepository {
    Site saveSite(Site site);

    List<Site> findAll();

    void delete(long id);

    Site findById(Long id);

}
