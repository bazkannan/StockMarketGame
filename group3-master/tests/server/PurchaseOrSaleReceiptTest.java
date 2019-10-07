package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PurchaseOrSaleReceiptTest {

    PurchaseOrSaleReceipt baz = new PurchaseOrSaleReceipt(
            "Baz", 30, 4444, 5, 9, 4, "Sugar",
            "rumour", "playersandequity");

    @Test
    public void getTimestamp() {

        int expectedValue = 30;
        int actualValue = baz.getTimestamp();
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void getCommodityID() {

        int expectedValue = 4444;
        int actualValue = baz.getCommodityID();
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void getQuantity() {

        int expectedValue = 4444;
        int actualValue = baz.getCommodityID();
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void getPlayerName() {

        String expectedValue = "Baz";
        String actualValue = baz.getPlayerName();
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void getPrice() {

        double expectedValue = 9;
        double actualValue = baz.getPrice();
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void getTransactionPlayerCost() {

        double expectedValue = 4;
        double actualValue = baz.getTransactionPlayerCost();
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void getPurchaseOrSale() {

        String expectedValue = "Sugar";
        String actualValue = baz.getPurchaseOrSale();
        assertEquals(expectedValue, actualValue);

    }
}