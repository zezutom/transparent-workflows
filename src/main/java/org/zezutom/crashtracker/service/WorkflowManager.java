package org.zezutom.crashtracker.service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 29/04/2012
 * Time: 15:06
 * Triggers the appropriate workflow and ensures it is set up properly before used.
 *
 */
public interface WorkflowManager {

    /**
     * Triggers the specified process and injects the incident in it.
     *
     * @param incidentId
     * @param processId
     */
    void process(Long incidentId, String processId);

    /**
     * Triggers the specified process, injects the incident and adds extra paramaters.
     *
     * @param incidentId
     * @param processId
     * @param model     any parameters out of incident's reach
     */
    void process(Long incidentId, String processId, Map<String, Object> model);

    /**
     * Completes a pending task.
     *
     * @param name
     * @param assignee
     */
    void completeTask(String name, String assignee);
}
