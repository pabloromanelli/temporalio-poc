package pabloromanelli.temporalio.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkflowA {
    @WorkflowMethod
    void start();
}
