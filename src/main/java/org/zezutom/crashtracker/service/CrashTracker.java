package org.zezutom.crashtracker.service;

import org.zezutom.crashtracker.model.IncidentOutput;
import org.zezutom.crashtracker.model.IncidentSeverity;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 22/04/2012
 * Time: 19:30
 *
 * Captures the incidents and provides the means of dealing with them.
 */
public interface CrashTracker {

    /**
     * Enables to report a brand new incident.
     *
     * @param description
     * @param severity
     * @return  incident ID
     */
    Long captureIncident(String description, IncidentSeverity severity);

    /**
     * Assigns an engineer to deal with an incident.
     *
     * @param incidentId
     * @param engineer
     * @return  true if the assignment has been successful, false otherwise
     */
    boolean assignEngineer(Long incidentId, String engineer);

    /**
     * Captures engineer's output.
     *
     * @param incidentId
     * @param output    mainly if the problem was fixed along with other details
     */
    void captureOutput(Long incidentId, IncidentOutput output);


}
