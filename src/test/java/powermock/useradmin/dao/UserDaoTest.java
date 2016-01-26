package powermock.useradmin.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import powermock.useradmin.dto.User;
import powermock.useradmin.util.IDGenerator;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by hanmomhanda on 2016-01-24.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IDGenerator.class)
public class UserDaoTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void create_should_return_userId() throws Exception {

        UserDao userDao = new UserDao();

        mockStatic(IDGenerator.class);
        when(IDGenerator.generateID()).thenReturn(1);

        int result = userDao.create(new User());

        assertEquals(1, result);
        verifyStatic();
    }
}