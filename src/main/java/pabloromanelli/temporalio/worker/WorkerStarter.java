package pabloromanelli.temporalio.worker;

import io.temporal.worker.WorkerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorkerStarter {
    private WorkerFactory factory;

    WorkerStarter(WorkerFactory factory) {
        this.factory = factory;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startWorker() {
        factory.start();
    }
}
