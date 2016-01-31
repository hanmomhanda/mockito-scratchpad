package mockito.greeting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanmomhanda on 2016-01-22.
 */
public class GreetingImplTest {

    private Greeting greeting;

    @Before
    public void setup() {
//        System.out.println(Thread.currentThread().getStackTrace()[1]);
        greeting = new GreetingImpl();
    }

    @Test
    public void greetShouldValidOutput() {
//        System.out.println(Thread.currentThread().getStackTrace()[1]);
        String result = greeting.greet("JUnit");
        assertNotNull(result);
        assertEquals("Hello JUnit", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void greet이Null이면Exception발생() {
//        System.out.println(Thread.currentThread().getStackTrace()[1]);
        greeting.greet(null);
        System.out.println("예외 발생 이후의 코드는 실행되지 않음");
    }

    @Test(expected = IllegalArgumentException.class)
    public void greet이Blank이면_Exception발생() {
//        System.out.println(Thread.currentThread().getStackTrace()[1]);
        greeting.greet("");
        System.out.println("예외 발생 이후의 코드는 실행되지 않음");
    }

    @After
    public void teardown() {
//        System.out.println(Thread.currentThread().getStackTrace()[1]);
        greeting = null;
    }
}
