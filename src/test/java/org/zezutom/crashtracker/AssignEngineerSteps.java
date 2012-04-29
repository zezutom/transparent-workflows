package org.zezutom.crashtracker;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.zezutom.crashtracker.model.Incident;
import org.zezutom.crashtracker.model.IncidentStatus;
import org.zezutom.crashtracker.service.CrashTracker;
import org.zezutom.crashtracker.service.IncidentManager;
import org.zezutom.crashtracker.util.AppUtil;
import org.zezutom.crashtracker.util.Steps;

import javax.annotation.Resource;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 29/04/2012
 * Time: 15:53
 *
 */
@Steps
public class AssignEngineerSteps {

    @Resource
    private CrashTracker crashTracker;

    @Resource
    private IncidentManager incidentManager;

    @Resource
    private TaskService taskService;

    private Long incidentId = 1L;

    private Task task;

    @Given("an unassigned incident exists")
    public void anIncident() {
        assertNotNull(findIncident());
    }

    @When("the incident is assigned to $engineer")
    public void anEngineerIsAssigned(String engineer) {
        boolean assigned = crashTracker.assignEngineer(incidentId, engineer);
        assertTrue(assigned);
    }

    @Then("the incident status should be $status")
    public void theIncidentStatusShouldBe(IncidentStatus status) {
        assertThat(findIncident().getStatus(), is(status));
    }

    @Then("the incident should be assigned to $assignee")
    public void theIncidentShouldBeAssignedTo(String assignee) {
        assertThat(findIncident().getAssignedTo(), is(assignee));
    }

    @Then("a new task should be created for $assignee")
    public void aNewTaskShouldBeCreatedFor(String assignee) {
        task = taskService.createTaskQuery().taskAssignee(assignee).singleResult();
        assertNotNull(task);
    }

    @Then("the task should be due in $hours hours")
    public void theTaskShouldBeDueIn(int hours) {
        final Date dueDate = task.getDueDate();

        assertThat(dueDate, greaterThan(AppUtil.now()));
        assertThat(dueDate, lessThanOrEqualTo(AppUtil.hoursFromNow(hours)));
    }

    @Then("the task description should be $description")
    public void theTaskDescriptionShouldBe(String description) {
        assertThat(task.getDescription(), is(description));
    }

    private Incident findIncident() {
        return incidentManager.find(incidentId);
    }

}
