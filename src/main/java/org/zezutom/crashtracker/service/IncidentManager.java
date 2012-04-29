package org.zezutom.crashtracker.service;

import org.zezutom.crashtracker.model.Incident;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 28/04/2012
 * Time: 19:05
 *
 */
public interface IncidentManager {

    Incident find(Long id);

    Long save(Incident incident);
}
