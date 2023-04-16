package pabloromanelli.temporalio.workflow;

import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowAImpl implements WorkflowA {
    private static final Logger log = LoggerFactory.getLogger(WorkflowAImpl.class);
    private MultipleActivities multipleActivities = Workflow.newActivityStub(MultipleActivities.class);

    @Override
    public void start() {
        String result = multipleActivities.doSomething("abc");
        // do something with the result
        log.info(result);
    }
}
