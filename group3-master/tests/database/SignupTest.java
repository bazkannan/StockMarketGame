package database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SignupTest {



    @Test
    public void testSignup() {

        int expectedUser = 0;
        int actualUser = Signup.Signup("Baz", "BazBaz", "baz@gmail.com");
        assertEquals(expectedUser, actualUser);

    }
}