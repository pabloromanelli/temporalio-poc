package pabloromanelli.temporalio;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowIdReusePolicy;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

import java.time.Duration;

public class StartWorkflow {
    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newConnectedServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget("localhost:7233")
                        .build(),
                Duration.ofSeconds(10)
        );
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowStub workflow = client.newUntypedWorkflowStub(
                "WorkflowA",
                WorkflowOptions.newBuilder()
                        // You should always use a business unique key to start workflows (to avoid duplicates)
                        .setWorkflowId("workflowUniqueId")
                        .setTaskQueue("default")
                        .setWorkflowIdReusePolicy(WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_REJECT_DUPLICATE)
                        .build()
        );
        // blocks until Workflow Execution has been started (not until it completes)
        WorkflowExecution instance = workflow.start();
        System.out.println("Started workflow with workflowId='"
                + instance.getWorkflowId()
                + "' and runId='"
                + instance.getRunId()
                + "'");
        // send signals
//        Workflow
//                .newUntypedExternalWorkflowStub("workflowId")
//                .signal("signalName", "param");
    }
}
