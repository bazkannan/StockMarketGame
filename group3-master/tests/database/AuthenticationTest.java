package database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthenticationTest {

    // Authentication baz = new Authentication("Baz", "BazBaz");

    @Test
    public void testServerAuthenticateUser() {

        int expectedUser = 1;
        int actualUser = Authentication.serverAuthenticateUser("Baz", "BazBaz");
        assertEquals(expectedUser, actualUser);

    }
}