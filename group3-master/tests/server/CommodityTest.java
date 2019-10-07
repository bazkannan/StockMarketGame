package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommodityTest {

    private Commodity gold = new Commodity("Gold", 543, 20);

    @Test
    public void add() {

        assertEquals(true, true);

    }

    @Test
    public void getItemID() {
        Commodity silver = new Commodity("Silver", 543, 20);
        int expectedItemID = 543;
        int actualItemID = silver.getItemID();
        assertEquals(expectedItemID, actualItemID);
    }

    @Test
    public void getName() {
        String expectedName = "Gold";
        String actualName = gold.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void getCurrentPrice() {
        double expectedCurrentPrice = 20;
        double actualCurrentPrice = gold.getCurrentPrice();
        assertEquals(expectedCurrentPrice, actualCurrentPrice, 0.1);
    }

    @Test
    public void getQuantity() {
        int expectedQuantity = 1000000;
        int actualQuantity = gold.getQuantity();
        assertEquals(expectedQuantity, actualQuantity);
    }
}