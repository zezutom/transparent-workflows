package org.zezutom.crashtracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zezutom.crashtracker.model.Incident;
import org.zezutom.crashtracker.model.IncidentOutput;
import org.zezutom.crashtracker.model.IncidentSeverity;
import org.zezutom.crashtracker.model.IncidentStatus;
import org.zezutom.crashtracker.util.AppUtil;
import org.zezutom.crashtracker.util.ModelBuilder;

import javax.annotation.Resource;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 22/04/2012
 * Time: 19:50
 *
 * The implementation driven by the Activiti workflow engine.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WorkflowDrivenCrashTracker implements CrashTracker {

    private static final Map<IncidentSeverity, Integer> reactionTime = new EnumMap<IncidentSeverity, Integer>(IncidentSeverity.class);

    static {
        reactionTime.put(IncidentSeverity.MAJOR, 2);
        reactionTime.put(IncidentSeverity.NORMAL, 24);
        reactionTime.put(IncidentSeverity.MINOR, 48);

    }
    @Resource
    private IncidentManager incidentManager;

    @Resource
    private WorkflowManager workflowManager;

    @Override
    public Long captureIncident(String description, IncidentSeverity severity) {
        // capture the incident
        final Long incidentId = incidentManager.save(new Incident(description, severity));

        // trigger the incident confirmation process
        workflowManager.process(incidentId, "confirmNewIncident");

        return incidentId;
     }

    @Override
    public boolean assignEngineer(Long incidentId, String engineer) {
        // get the incident
        Incident incident = findIncident(incidentId);

        // update it
        incident.setAssignedTo(engineer);
        incident.setStatus(IncidentStatus.ASSIGNED);
        incidentManager.save(incident);

        // define the deadline
        final Date dueDate = AppUtil.hoursFromNow(reactionTime.get(incident.getSeverity()));

        // assign the engineer
        workflowManager.process(incidentId, "createAssignment", new ModelBuilder().add("dueDate", dueDate).build());

        // no failures
        return true;
    }

    @Override
    public void captureOutput(Long incidentId, IncidentOutput output) {

        // get the incident
        Incident incident = findIncident(incidentId);

        // add the output to it
        incident.addOutput(output);

        // close the incident if it has been resolved
        if (output.isResolved()) {
            incident.setStatus(IncidentStatus.CLOSED);
            workflowManager.completeTask("resolve the incident", incident.getAssignedTo());
        }
        incidentManager.save(incident);
    }

    private Incident findIncident(Long id) {
        return incidentManager.find(id);
    }
}
