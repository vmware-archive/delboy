package io.pivotal.labs.delboy.test;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class JerseyTestRule extends JerseyTest implements TestRule {

    public JerseyTestRule(Class<?>... resourceClasses) {
        super(application(resourceClasses));
    }

    private static ResourceConfig application(Class<?>[] resourceClasses) {
        if (resourceClasses.length == 0) {
            throw new IllegalArgumentException("at least one resource class must be specified");
        }
        return new ResourceConfig(resourceClasses);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                setUp();
                try {
                    base.evaluate();
                } finally {
                    tearDown();
                }
            }
        };
    }

}
