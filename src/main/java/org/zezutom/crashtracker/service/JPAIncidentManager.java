package org.zezutom.crashtracker.service;

import org.springframework.stereotype.Service;
import org.zezutom.crashtracker.model.Incident;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 25/04/2012
 * Time: 07:04
 *
 */
@Service
public class JPAIncidentManager implements IncidentManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Incident find(Long id) {
        return entityManager.find(Incident.class, id);
    }

    @Override
    public Long save(Incident incident) {

        if (incident.getId() == null) {
            entityManager.persist(incident);
        } else {
            incident = entityManager.merge(incident);
        }
        return incident.getId();
    }

}

