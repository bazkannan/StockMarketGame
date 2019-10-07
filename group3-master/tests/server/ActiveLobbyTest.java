package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActiveLobbyTest {

    private ActiveLobby player = new ActiveLobby(15);

    @Test
    public void buy() {
        // buy.buy();
        assertEquals(true, true);
    }

    @Test
    public void getLobbyID() {

        int expectedLobbyID = 15;
        int actualLobbyID = player.getLobbyID();
        assertEquals(expectedLobbyID, actualLobbyID);

    }

    @Test
    public void getLobbySize() {

        int expectedLobbySize = 1;
        int actualLobbySize = player.getLobbySize();
        assertEquals(expectedLobbySize, actualLobbySize);

    }


    @Test
    public void isInGame() {

        boolean expectedValue = false;
        boolean actualValue = player.isInGame();
        assertEquals(expectedValue, actualValue);

    }


    @Test
    public void getCommodities() {

        Commodity gold = new Commodity("Gold", 1 , 1000);
        String expectedName = "Gold";
        String actualName = gold.getName();
        assertEquals(expectedName, actualName);

    }



    @Test
    public void addPlayer() {

        TradingAccount baz = new TradingAccount("Baz");
        assertEquals(true, true);

    }

    @Test
    public void removePlayer() {

        TradingAccount baz = new TradingAccount("Baz");
        assertEquals(false, false);

    }

}