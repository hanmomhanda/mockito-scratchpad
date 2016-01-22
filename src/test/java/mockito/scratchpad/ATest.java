package mockito.scratchpad;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by hanmomhanda on 2016-01-22.
 */
public class ATest {

    @Mock
    B b;

    private A a;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        a = new A(b);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = RuntimeException.class)
    public void useVoidMethod_should_throw_RuntimeException() throws Exception {
        doThrow(RuntimeException.class).when(b).voidMethod();

        a.useVoidMethod();
    }

    @Test
    public void useVoidMethod_should_call_the_voidMethod() throws Exception {
        doNothing().when(b).voidMethod();

        assertSame(1, a.useVoidMethod());
    }

    @Test(expected = RuntimeException.class)
    public void test_consecutive_calls() throws Exception {
        doNothing().doThrow(RuntimeException.class).when(b).voidMethod();

        a.useVoidMethod();
        verify(b).voidMethod();

        a.useVoidMethod();
        verify(b).voidMethod();
    }
}