package org.zezutom.crashtracker;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.zezutom.crashtracker.model.Incident;
import org.zezutom.crashtracker.model.IncidentSeverity;
import org.zezutom.crashtracker.model.IncidentStatus;
import org.zezutom.crashtracker.service.CrashTracker;
import org.zezutom.crashtracker.service.IncidentManager;
import org.zezutom.crashtracker.util.AppUtil;
import org.zezutom.crashtracker.util.Steps;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 24/04/2012
 * Time: 07:53
 *
 */
@Steps
public class ReportIncidentSteps {

    @Resource
    private CrashTracker crashTracker;

    @Resource
    private IncidentManager incidentManager;

    private Incident incident;

    @Given("a customer called $username is logged in")
    public void aCustomer(String username) {
        AppUtil.login(username);
        assertThat(username, is(AppUtil.getUsername()));

    }

    @When("an incident described as $description is submitted as a $severity problem")
    public void anIncidentIsSubmitted(String description, IncidentSeverity severity) {
        Long incidentId = crashTracker.captureIncident(description, severity);
        assertNotNull(incidentId);

        incident = incidentManager.find(incidentId);
        assertNotNull(incident);
    }

    @Then("the new incident should be described as $description")
    public void theDescriptionShouldBe(String description) {
        assertThat(incident.getDescription(), is(description));
    }

    @Then("the new incident should be classified as $severity")
    public void theSeverityShouldBe(IncidentSeverity severity) {
        assertThat(incident.getSeverity(), is(severity));
    }

    @Then("the new incident should be bound to $customer")
    public void theCustomerShouldBe(String customer) {
        assertThat(incident.getCreatedBy(), is(AppUtil.getUsername()));
    }

    @Then("the submission time should be the current time")
    public void theSubmissionTimeShouldBeNow() {
        final Date created = incident.getCreated();
        assertNotNull(created);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        assertThat(dateFormat.format(created), is(dateFormat.format(AppUtil.now())));
    }

    @Then("the status should be $status")
    public void theStatusShouldBe(IncidentStatus status) {
        assertThat(incident.getStatus(), is(status));
    }

    @Then("a new email should be sent to $recipient, subject: $subject, message: $message")
    public void anEmailShouldBeSentTo(String recipient, String subject, String message) throws MessagingException, IOException {
        StoryTest.verifyMail(recipient, subject, message);
    }

}

