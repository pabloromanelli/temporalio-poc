package pabloromanelli.temporalio.workflow;

import org.springframework.stereotype.Component;

@Component
public class MultipleActivitiesImpl implements MultipleActivities {
    @Override
    public String doSomething(String someValue) {
        return "result X";
    }
}
