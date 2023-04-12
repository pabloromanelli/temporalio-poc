package pabloromanelli.temporalio;

import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkflowImplementationOptions;

import java.time.Duration;

public class StartWorker {
    public static final String QUEUE_NAME = "MainQueue";

    public static void main(String[] args) {
//        // see the Micrometer documentation for configuration details on other supported monitoring systems.
//        // in this example shows how to set up Prometheus registry and stats reported.
//        PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
//        StatsReporter reporter = new MicrometerClientStatsReporter(registry);
//        // set up a new scope, report every 10 seconds
//        Scope scope = new RootScopeBuilder()
//                .reporter(reporter)
//                .reportEvery(com.uber.m3.util.Duration.ofSeconds(10));
//        // for Prometheus collection, expose a scrape endpoint.

        WorkflowServiceStubs service = WorkflowServiceStubs.newConnectedServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget("localhost:7233")
                        //.setEnableKeepAlive(true)
                        //.setMetricsScope(scope)
                        .build(),
                Duration.ofSeconds(10)
        );
        WorkflowImplementationOptions workflowOptions = WorkflowImplementationOptions.newBuilder()
                .setDefaultActivityOptions(ActivityOptions.newBuilder()
                        .setStartToCloseTimeout(Duration.ofSeconds(5))
                        // if task queue not set, it will be same Task Queue as what the Workflow uses
                        .setTaskQueue(QUEUE_NAME)
                        // activities will only be retried if setMaximumAttempts or setScheduleToCloseTimeout are used
                        .setRetryOptions(RetryOptions.newBuilder()
                                .setMaximumAttempts(3)
                                .build())
                        .build())
                .build();

        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(workflowOptions, WorkflowAImpl.class);
        worker.registerActivitiesImplementations(new MultipleActivitiesImpl());

        factory.start();
        // TODO healthcheck?
        // TODO metrics?
        System.out.println("started");
    }
}