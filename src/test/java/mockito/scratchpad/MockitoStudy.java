package mockito.scratchpad;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by hanmomhanda on 2016-01-31.
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoStudy {

    @Mock
    List mockedList;

    // @RunWith(MockitoJUnitRunner.class) 애노테이션 해주면 MockitoAnnotations.initMocks(this) 불필요
    @Before
    public void setup() throws Exception {
//        MockitoAnnotations.initMocks(this);
        mockedList.clear();
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
        verify(mockedList, times(1)).get(0);

        // consecutive stubbing
        String[] strings = {"get 0", "get 1", "get 2", "get 3"};
        when(mockedList.get(anyInt())).thenReturn(strings);

        System.out.println(mockedList.get(anyInt()));
        System.out.println(mockedList.get(anyInt()));
        System.out.println(mockedList.get(anyInt()));
        System.out.println(mockedList.get(anyInt()));
        System.out.println(mockedList.get(anyInt()));

        // 앞에서 verify 했더라도 호출회수는 전체 호출회수로 검증
        verify(mockedList, times(6)).get(anyInt());
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

    @Test
    public void t05_null_when_not_mocked() throws Exception {
        // stubbing
        when(mockedList.get(0)).thenReturn("first");

        System.out.println(mockedList.get(0));

        // when-thenReturn으로 stubbing 되지 않으면 null 반환
        System.out.println(mockedList.get(999));

        verify(mockedList).get(0);
        // 호출 내용이 stubbing 되지는 않았지만
        // mockedList는 mock 된 객체이므로 행위자체는 기록되어 verify 성공
        verify(mockedList).get(999);
    }

    @Test
    public void t06_verificatino_in_order() throws Exception {
        // 동일 객체 내의 메서드 호출 순서 검증
        mockedList.add("first");
        mockedList.add("second");

        InOrder inOrder = inOrder(mockedList);

        // InOrder 생성 후에도 순서 추적 적용됨
        mockedList.add("third");
        inOrder.verify(mockedList).add("first");
        inOrder.verify(mockedList).add("second");
        inOrder.verify(mockedList).add("third");

        // 여러 객체의 순서도 검증 가능
        List tmpList = mock(List.class);
        InOrder inOrderMultiClass = inOrder(mockedList, tmpList);
        mockedList.add("FIRST");
        tmpList.add("LATTER");
        inOrderMultiClass.verify(mockedList).add("FIRST");
        inOrderMultiClass.verify(tmpList).add("LATTER");
    }

    @Test
    public void t07_stubbing_consecutive() throws Exception {
        when(mockedList.add("element")).thenReturn(true, false);

        System.out.println(mockedList.add("element"));
        System.out.println(mockedList.add("element"));
        System.out.println(mockedList.add("element")); // 마지막 return 값 적용
        System.out.println(mockedList.add("element")); // 마지막 return 값 적용

        verify(mockedList, times(4)).add("element");
    }

    @Test
    public void t08_spying_doReturn() throws Exception {
        List list = new LinkedList<>();
        List spy = spy(list);
        assertThat(spy, is(list)); // 같게 나온다.. 뭐지?
        assertNotEquals(spy, list); // 얘들은 또 다르다..


        // spy는 비어있으므로 spy.get(0)을 stubbing 하면 IndexOutOfBoudnsException 발생
//        when(spy.get(0)).thenReturn(3);
        // spy.get(0)이 아니라 spy를 stubbing 하면 OK
        doReturn(3).when(spy).get(0);

        when(spy.size()).thenReturn(100);

        spy.add("one");
        spy.add("two");

        System.out.println(spy.get(0));
        System.out.println(spy.size());

        System.out.println(list.size());

        verify(spy).add("one");
        verify(spy).add("two");
    }

    @Test
    public void t09_argumentCaptor() throws Exception {
        class Person {
            private String name;

            public Person(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        Person aPerson = new Person("John");

        mockedList.add(aPerson);

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(mockedList).add(argument.capture());
        System.out.println(aPerson);
        // 아래와 같이 argument.capture()를 verify나 stubbing 밖에서 사용하면 에러 발생
//        System.out.println(argument.capture());

        assertEquals("John", argument.getValue().getName());
    }

    @Test
    public void t10_enable_partialMock_without_spy() throws Exception {
        class Person {
            private String name;
            private int height;

            public Person(String name, int height) {
                this.name = name;
                this.height = height;
            }

            public String getName() {
                return name;
            }

            public int getHeight() {
                return height;
            }

            public int printSome() {
                System.out.println("SOME");
                return -1;
            }
        }
        Person partialMock = mock(Person.class);
        when(partialMock.printSome()).thenCallRealMethod();
        when(partialMock.getName()).thenReturn("MOCKED");

        partialMock.printSome();
        System.out.println(partialMock.getName());
        System.out.println(partialMock.getHeight());

        verify(partialMock).printSome();
        verify(partialMock).getName();
    }

    @Test
    public void t11_bdd_style() throws Exception {
        //given
        org.mockito.BDDMockito.given(mockedList.size()).willReturn(3);

        //when
        int length = mockedList.size();

        //then
        verify(mockedList, never()).add(any());
        System.out.println(mockedList.size());
        assertEquals(mockedList.size(), length);

    }
}
