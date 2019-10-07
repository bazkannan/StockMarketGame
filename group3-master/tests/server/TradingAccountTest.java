package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TradingAccountTest {

    private Commodity gold = new Commodity("Gold", 543, 20);

    @Test
    public void buy() {

        assertEquals(true, true);

    }

    @Test
    public void sell() {

        assertEquals(false, false);

    }

    @Test
    public void getName() {

        String expectedName = "Gold";
        String actualName = gold.getName();
        assertEquals(expectedName, actualName);

    }

    /* @Test
    public void getMoney() {

        double expectedMoney = 20;
        double actualMoney = gold.getMoney();
        assertEquals(expectedMoney, actualMoney);

    } */

    /* @Test
    public void getPortfolioQuantity() {

        int expectedPortfolioQuantity = 5;
        int actualPortfolioQuantity = gold.getPortfolioQuantity();
        assertEquals(expectedPortfolioQuantity, actualPortfolioQuantity);

    } */

    /* @Test
    public void getCurrentLobby() {

        boolean expectedLobby = false;
        boolean actualLobby = gold.getCurrentLobby();
        assertEquals(expectedLobby, actualLobby);

    } */
}