package org.zezutom.crashtracker.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;
import org.zezutom.crashtracker.util.ModelBuilder;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 29/04/2012
 * Time: 15:09
 *
 * Manages Activiti workflows.
 *
 */
@Service
public class ActivitiWorkflowManager implements WorkflowManager {

    public static final String PROCESS_VARIABLE = "incident";

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IncidentManager incidentManager;

    private String deploymentId;

    @Override
    public void process(Long incidentId, String processId) {
        process(incidentId, processId, null);
    }

    @Override
    public void process(Long incidentId, String processId, Map<String, Object> model) {
        if (deploymentId == null) {
            deploy();
        }

        if (model == null) {
            model = new ModelBuilder().build();
        }

        model.put(PROCESS_VARIABLE, incidentManager.find(incidentId));

        // trigger the incident confirmation process
        runtimeService.startProcessInstanceByKey(processId, model);
    }

    private void deploy() {
        deploymentId = repositoryService
                .createDeployment()
                .addClasspathResource("workflows/report_incident.bpmn20.xml")
                .addClasspathResource("workflows/assign_engineer.bpmn20.xml")
                .deploy().getId();
    }

}
