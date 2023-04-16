package pabloromanelli.temporalio.workflow;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface MultipleActivities {
    String doSomething(String someValue);
}
