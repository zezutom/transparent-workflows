package org.zezutom.crashtracker;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.zezutom.crashtracker.model.IncidentOutput;
import org.zezutom.crashtracker.model.IncidentStatus;
import org.zezutom.crashtracker.service.CrashTracker;
import org.zezutom.crashtracker.util.Steps;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 01/05/2012
 * Time: 07:52
 *
 */
@Steps
public class CloseIncidentSteps {

    public static final String ENGINEER = "John Doe";

    @Resource
    private CrashTracker crashTracker;

    @Resource
    private TaskService taskService;

    private Long incidentId = 2L;

    @Given("an assignment exists")
    public void anAssignment() {
        assertTrue(crashTracker.assignEngineer(incidentId, ENGINEER));
    }

    @When("the assignment is resolved as $description")
    public void theAssignmentIsResolvedAs(String description) {
        crashTracker.captureOutput(incidentId, new IncidentOutput(description));
    }

    @Then("the incident status should be $status")
    public void theIncidentStatusShouldBe(IncidentStatus status) {
        StoryTest.assertIncidentStatus(incidentId, status);
    }

    @Then("the incident should be closed as $description")
    public void theResolutionShouldBe(String description) {
        StoryTest.assertIncidentStatus(incidentId, IncidentStatus.CLOSED);
        IncidentOutput output = StoryTest.findIncident(incidentId).getLastOutput();
        assertNotNull(output);
        assertTrue(output.isResolved());
        assertThat(output.getDescription(), is(description));
    }

    @Then("no pending task should be assigned to the engineer")
    public void noPendingTaskShouldBeAssignedToTheEngineer() {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(ENGINEER).list();
        assertTrue(tasks.isEmpty());
    }

}
