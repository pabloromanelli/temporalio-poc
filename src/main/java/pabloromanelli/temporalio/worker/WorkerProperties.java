package pabloromanelli.temporalio.worker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("temporal")
public record WorkerProperties(
        String address,
        String workflowQueue,
        String activitiesQueue
) {
}
