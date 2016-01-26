package powermock.useradmin.dao;

import powermock.useradmin.dto.User;
import powermock.useradmin.util.IDGenerator;

/**
 * Created by hanmomhanda on 2016-01-24.
 */
public class UserDao {

    public int create(User user) {
        int id =  IDGenerator.generateID();
        // Save the user to the DB
        return id;
    }
}
