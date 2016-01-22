package junit.parameterized.calculator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by hanmomhanda on 2016-01-22.
 */
@RunWith(Parameterized.class)
public class CalculatorImplTest {

    private int num1;
    private int num2;
    private int expectedResult;

    public CalculatorImplTest(int num1, int num2, int expectedResult) {
        this.num1 = num1;
        this.num2 = num2;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Integer[]> data() {
        return Arrays.asList(new Integer[][] {{-1, 2, 1}, {1, 2, 3}, {6, 7, 13}});
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add_should_return_result() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl();
        int result = calculator.add(num1, num2);
        assertEquals(expectedResult, result);
    }
}