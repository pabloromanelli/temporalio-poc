package pabloromanelli.temporalio;

import io.temporal.workflow.Workflow;

public class WorkflowAImpl implements WorkflowA {
    private MultipleActivities multipleActivities = Workflow.newActivityStub(MultipleActivities.class);

    @Override
    public void start() {
        String result = multipleActivities.doSomething("abc");
        // do something with the result
        System.out.println(result);
    }
}
