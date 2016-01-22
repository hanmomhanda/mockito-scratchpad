package mockito.scratchpad;

/**
 * Created by hanmomhanda on 2016-01-22.
 */
public class A {

    private B b;

    public A(B b) {
        this.b = b;
    }

    public int useVoidMethod() {
        try {
            b.voidMethod();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return 1;
    }
}
