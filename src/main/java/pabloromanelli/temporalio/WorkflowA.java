package pabloromanelli.temporalio;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkflowA {
    @WorkflowMethod
    void start();
}
