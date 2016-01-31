package mockito.scratchpad;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by hanmomhanda on 2016-01-31.
 */
public class MockitoStudy {

    @Mock
    List mockedList;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void t01_verify() throws Exception {
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void t02_verify() throws Exception {
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList, never()).add("two");
        verify(mockedList, atLeast(1)).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void t03_when_thenReturn() throws Exception {
        // stubbing
        when(mockedList.get(0)).thenReturn("first");

        System.out.println(mockedList.get(0));

        verify(mockedList).get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void t04_when_thenThrow() throws Exception {
        // stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new IndexOutOfBoundsException());

        System.out.println(mockedList.get(1));

        // 예외 던진 이후 구문은 실행되지 않음
        System.out.println("Statements after thenThrow");

        verify(mockedList).get(0);
        verify(mockedList).get(1);
    }
}
