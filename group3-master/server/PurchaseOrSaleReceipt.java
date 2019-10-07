package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PurchaseOrSaleReceipt implements Serializable {

    private String playerName;
    private int timestamp;
    private int commodityID;
    private int quantity;
    private double price;
    private double transactionPlayerCost;
    private String purchaseOrSale;
    private String rumourMessage;
    private String players;

    public PurchaseOrSaleReceipt(String player, int time, int commodityID , int quantity, double newCommodityPrice , double cost , String type , String rumour, String playersAndEquity ) {
        this.playerName = player;
        this.timestamp = time;
        this.commodityID = commodityID;
        this.quantity = quantity;
        this.price = newCommodityPrice;
        this.transactionPlayerCost = cost;
        this.purchaseOrSale = type;
        this.rumourMessage = rumour;
        this.players = playersAndEquity;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getCommodityID() {
        return commodityID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getPrice() {
        return price;
    }

    public double getTransactionPlayerCost() {
        return transactionPlayerCost;
    }

    public String getPurchaseOrSale() {
        return purchaseOrSale;
    }


    public String getRumourMessage() {
        return rumourMessage;
    }

    public String getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "PurchaseOrSaleReceipt{" +
                "playerName='" + playerName + '\'' +
                ", timestamp=" + timestamp +
                ", commodityID=" + commodityID +
                ", quantity=" + quantity +
                ", price=" + price +
                ", transactionPlayerCost=" + transactionPlayerCost +
                ", purchaseOrSale='" + purchaseOrSale + '\'' +
                ", rumourMessage='" + rumourMessage + '\'' +
                ", players='" + players + '\'' +
                '}';
    }
}
