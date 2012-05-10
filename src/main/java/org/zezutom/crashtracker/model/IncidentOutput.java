package org.zezutom.crashtracker.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 22/04/2012
 * Time: 19:45
 *
 * Tells whether the incident has been resolved and what the cause of the failure was.
 */
@Entity
@Table(name = "incident_outputs")
public class IncidentOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean resolved;

    private String description;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;

    public IncidentOutput() {}

    public IncidentOutput(String description) {
        this(true, description);
    }

    public IncidentOutput(boolean resolved, String description) {
        this.resolved = resolved;
        this.description = description;
    }

    public boolean isResolved() {
        return resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public Incident getIncident() {
        return incident;
    }
}
