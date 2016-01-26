package powermock.useradmin.util;

/**
 * Created by hanmomhanda on 2016-01-24.
 */
public final class IDGenerator {

        static int i;

        public static final int generateID() {
            return i++;
        }
}
