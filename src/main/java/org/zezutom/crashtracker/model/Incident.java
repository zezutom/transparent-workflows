package org.zezutom.crashtracker.model;

import org.zezutom.crashtracker.util.AppUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 22/04/2012
 * Time: 19:22
 *
 * Describes the reported problem, typically some kind of equipment failure.
 */
@Entity
@Table(name = "incidents")
public class Incident implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column
    private Date deadline;

    @Enumerated(value = EnumType.STRING)
    private IncidentStatus status = IncidentStatus.SUBMITTED;

    @Enumerated(value = EnumType.STRING)
    private IncidentSeverity severity;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created = AppUtil.now();

    @Column(name = "created_by")
    private String createdBy = AppUtil.getUsername();

    @Column(name = "assigned_to")
    private String assignedTo;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "incident")
    private List<IncidentOutput> outputs;

    public Incident() {}

    public Incident(String description, IncidentSeverity severity) {
        this.description = description;
        this.severity = severity;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public IncidentSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(IncidentSeverity severity) {
        this.severity = severity;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void addOutput(IncidentOutput output) {
        if (outputs == null) {
            outputs = new ArrayList<IncidentOutput>();
        }
        outputs.add(output);
        output.setIncident(this);
    }

    public List<IncidentOutput> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }

    public IncidentOutput getLastOutput() {
        return (outputs == null || outputs.isEmpty()) ? null : outputs.get(outputs.size() - 1);
    }
}
