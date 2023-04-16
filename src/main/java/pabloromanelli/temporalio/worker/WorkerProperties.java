package pabloromanelli.temporalio.worker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("worker")
public record WorkerProperties(
        String clusterHostPort,
        String workflowQueue,
        String activitiesQueue
) {
}
