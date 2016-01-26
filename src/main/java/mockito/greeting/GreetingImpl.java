package mockito.greeting;

/**
 * Created by hanmomhanda on 2016-01-22.
 */
public class GreetingImpl implements Greeting {
    @Override
    public String greet(String name) {
        if (name == null || name.length() == 0)
            throw new IllegalArgumentException();
        return "Hello " + name;
    }
}
