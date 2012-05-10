package org.zezutom.crashtracker;

import org.activiti.engine.IdentityService;
import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import org.zezutom.crashtracker.model.Incident;
import org.zezutom.crashtracker.model.IncidentStatus;
import org.zezutom.crashtracker.service.IncidentManager;
import org.zezutom.crashtracker.util.MailParser;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 25/04/2012
 * Time: 08:17
 *
 * Makes use of the JBehave's Spring support.
 */
@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure(parameterConverters = ParameterConverters.EnumConverter.class)
@UsingEmbedder(
        embedder = Embedder.class,
        generateViewAfterStories = true,
        ignoreFailureInStories = true,
        ignoreFailureInView = true)
@UsingSpring(resources = {"classpath:app-config.xml"})
public class StoryTest extends InjectableEmbedder {

    private static Wiser wiser;

    private static IncidentManager incidentManager;

    public StoryTest() {
        incidentManager = new ClassPathXmlApplicationContext("app-config.xml").getBean(IncidentManager.class);
        assertNotNull(incidentManager);
    }

    @Before
    public void setUp() {
        wiser = new Wiser();
        wiser.setPort(2500);
        wiser.start();
    }

    @After
    public void tearDown() {
        wiser.stop();
    }

    public static void verifyMail(String to, String subject, String text) throws MessagingException, IOException {
        List<WiserMessage> messages = wiser.getMessages();
        assertNotNull(messages);
        assertThat(messages.size(), is(1));

        WiserMessage message = messages.get(0);
        assertNotNull(message);

        MailParser mailParser = new MailParser(message.getMimeMessage());

        assertThat(mailParser.getRecipient(), containsString(to));
        assertThat(mailParser.getSubject(), is(subject));
        assertThat(mailParser.getBody(), containsString(text));
    }

    @Test
    @Override
    public void run() throws Throwable {
        injectedEmbedder().runStoriesAsPaths(storyPaths());
    }

    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromPath("src/main/resources"), "**/*.story", "");
    }

    // Utility methods

    public static Incident findIncident(Long id) {
        return incidentManager.find(id);
    }

    public static void assertIncidentStatus(Long id, IncidentStatus status) {
        assertThat(findIncident(id).getStatus(), is(status));
    }


}
