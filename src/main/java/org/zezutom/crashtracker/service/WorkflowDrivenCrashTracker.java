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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long captureIncident(String description, IncidentSeverity severity) {
        // capture the incident
        final Long incidentId = incidentManager.save(new Incident(description, severity));

        // trigger the incident confirmation process
        workflowManager.process(incidentId, "confirmNewIncident");

        return incidentId;
     }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean assignEngineer(Long incidentId, String engineer) {
        // get the incident
        Incident incident = incidentManager.find(incidentId);

        // update it
        incident.setAssignedTo(engineer);
        incident.setStatus(IncidentStatus.ASSIGNED);
        incidentManager.save(incident);

        // define the deadline
        final Date dueDate = AppUtil.hoursFromNow(reactionTime.get(incident.getSeverity()));

        // let the assignment be completed
        workflowManager.process(incidentId, "assignEngineer", new ModelBuilder().add("dueDate", dueDate).build());

        // no failures
        return true;
    }

    @Override
    public void captureOutput(Long incidentId, IncidentOutput output) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
