package pabloromanelli.temporalio;

public class MultipleActivitiesImpl implements MultipleActivities {
    @Override
    public String doSomething(String someValue) {
        return "result X";
    }
}
