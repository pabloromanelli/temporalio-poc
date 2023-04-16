package pabloromanelli.temporalio.worker;

import io.temporal.api.workflowservice.v1.GetClusterInfoRequest;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TemporalHealthIndicator implements HealthIndicator {
    private final WorkflowServiceStubs service;

    TemporalHealthIndicator(WorkflowServiceStubs service) {
        this.service = service;
    }

    @Override
    public Health health() {
        if (service.blockingStub().getClusterInfo(GetClusterInfoRequest.newBuilder().build()).isInitialized())
            return Health.up().build();
        else
            return Health.down().build();
    }
}
