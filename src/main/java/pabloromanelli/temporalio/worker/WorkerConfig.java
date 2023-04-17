package pabloromanelli.temporalio.worker;

import com.uber.m3.tally.RootScopeBuilder;
import com.uber.m3.tally.Scope;
import com.uber.m3.tally.StatsReporter;
import io.micrometer.core.instrument.MeterRegistry;
import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.common.RetryOptions;
import io.temporal.common.reporter.MicrometerClientStatsReporter;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkflowImplementationOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pabloromanelli.temporalio.workflow.MultipleActivitiesImpl;
import pabloromanelli.temporalio.workflow.WorkflowAImpl;

import java.time.Duration;

@Configuration
public class WorkerConfig {

    @Bean
    public Scope metricsScope(MeterRegistry registry) {
        StatsReporter reporter = new MicrometerClientStatsReporter(registry);
        return new RootScopeBuilder()
                .reporter(reporter)
                .reportEvery(com.uber.m3.util.Duration.ofSeconds(10));
    }

    @Bean
    public WorkflowServiceStubs service(Scope scope, WorkerProperties properties) {
        return WorkflowServiceStubs.newConnectedServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(properties.address())
                        .setMetricsScope(scope)
                        .build(),
                Duration.ofSeconds(10)
        );
    }

    /**
     * It gracefully shuts down the worker when Spring is closing
     */
    @Bean
    public WorkerFactory workerFactory(
            WorkflowServiceStubs service,
            MultipleActivitiesImpl activities,
            WorkerProperties properties
    ) {
        WorkflowImplementationOptions workflowOptions = WorkflowImplementationOptions.newBuilder()
                .setDefaultActivityOptions(ActivityOptions.newBuilder()
                        .setStartToCloseTimeout(Duration.ofSeconds(5))
                        // if task queue not set, it will be same Task Queue as what the Workflow uses
                        .setTaskQueue(properties.activitiesQueue())
                        // activities will only be retried if setMaximumAttempts or setScheduleToCloseTimeout are used
                        .setRetryOptions(RetryOptions.newBuilder()
                                .setMaximumAttempts(3)
                                .build())
                        .build())
                .build();

        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(properties.workflowQueue());

        // register workflows & activities
        worker.registerWorkflowImplementationTypes(workflowOptions, WorkflowAImpl.class);
        worker.registerActivitiesImplementations(activities);
        return factory;
    }

}
