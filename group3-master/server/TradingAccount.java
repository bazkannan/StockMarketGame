package server;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.toIntExact;

/**
 * @author group3.Anfan
 */
public class TradingAccount {

    private String name;
    private double money;
    private int[] portfolioQuantity;
    private ActiveLobby currentLobby;
    private int GUIScreen;
    private double equity;



    // CONSTRUCTOR for TradingAccount
    public TradingAccount(String name){
        this.name = name;
        this.money = 1000000;
        this.portfolioQuantity = new int[]{0,0,0,0,0,0,0,0,0,0};
        this.currentLobby = null;
        this.GUIScreen = 0;
        this.equity = 1000000;
    }

    // A method for TradingAccount buying a Commodity object from the market
    // and updating its portfolio.
    public synchronized PurchaseOrSaleReceipt buy(String commodity, String quantity){

        int globalID = Integer.parseInt(commodity);
        int buyQuantity = Integer.parseInt(quantity);

        Commodity globalCommod = currentLobby.getCommodities()[globalID];

        String nameCommodity = globalCommod.getName();
        double price = globalCommod.getCurrentPrice();
        System.out.println(price);
        int currentGlobalQuantity = globalCommod.getQuantity();
        System.out.println(currentGlobalQuantity);

        // check if you have enough money && there is enough quantity on the market
        if (buyQuantity * price < this.money && buyQuantity < currentGlobalQuantity){

            // update quantity in portfolio
            portfolioQuantity[globalID] =+ buyQuantity;

            // update the global quantity
            globalCommod.setQuantity(currentGlobalQuantity - buyQuantity);

            // update user's money
            this.money = money - (buyQuantity * price);

            // GLOBAL PRICES CURRENTLY DO NOT CHANGE AS YOU BUY / SELL
            // NEED TO CHANGE

            System.out.printf("%d of %s was successfully bought.\n", buyQuantity, nameCommodity);


            String playerInstance = this.currentLobby.generatePlayersAndEquityString();
            int timeStampInt = toIntExact(this.currentLobby.gameTimer);
            PurchaseOrSaleReceipt purchaseRecipt = new PurchaseOrSaleReceipt(this.name , timeStampInt , globalID , buyQuantity , price , (buyQuantity * price), "buy" , "empty", playerInstance);
            this.currentLobby.transactionHistory.add(purchaseRecipt);
            System.out.println(purchaseRecipt.toString());
            return purchaseRecipt;
        // otherwise
        } else {
            System.out.println("Cannot buy, either quantity invalid or cash insufficient.");
        }
        return null;

    }

    // A method for TradingAccount selling a Commodity object
    // updating portfolio in portfolio
    public synchronized PurchaseOrSaleReceipt sell(String commodity, String quantity){

        int globalID = Integer.parseInt(commodity);
        int sellQuantity = Integer.parseInt(quantity);

        Commodity globalCommod = currentLobby.getCommodities()[globalID];
        String commodName = globalCommod.getName();
        int globalQuantity = globalCommod.getQuantity();
        double sellPrice = globalCommod.getCurrentPrice();

        // IF YOU OWN ENOUGH OF ITEM IN YOUR PORTFOLIO
        if (portfolioQuantity[globalID] >= sellQuantity){

            // update the quantity in your portfolio
            portfolioQuantity[globalID] -= sellQuantity;

            // update the global quantity
            globalCommod.setQuantity(globalQuantity + sellQuantity);

            // update your money
            this.money += sellQuantity * sellPrice;


            // GLOBAL PRICES CURRENTLY DO NOT CHANGE AS YOU BUY / SELL
            // NEED TO CHANGE
            String playerInstance = this.currentLobby.generatePlayersAndEquityString();
            int timeStampInt = toIntExact(this.currentLobby.gameTimer);
            PurchaseOrSaleReceipt purchaseRecipt = new PurchaseOrSaleReceipt(this.name , timeStampInt , globalID , sellQuantity , sellPrice , (sellQuantity * sellPrice), "sell" , "empty", playerInstance);
            this.currentLobby.transactionHistory.add(purchaseRecipt);
            System.out.println(purchaseRecipt.toString());
            return purchaseRecipt;


        // IF THE COMMODITY IS NOT IN YOUR PORTFOLIO
        } else {
            System.out.println(commodName + " is not in the portfolio or " +
                    "the sellQuantity you want to sell is too high. You cannot sell.");
            return null;

        }

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int[] getPortfolioQuantity() {
        return portfolioQuantity;
    }

    public ActiveLobby getCurrentLobby() {
        return currentLobby;
    }

	public void setGUIScreen(int gUIScreen) {
		GUIScreen = gUIScreen;
	}

    public void setCurrentLobby(ActiveLobby currentLobby) {
        this.currentLobby = currentLobby;
    }

    public double getEquity() {
        return equity;
    }

    public void setEquity(double equity) {
        this.equity = equity;
    }

    public void updateEquity(){
        double cash = getMoney();

        double ownedCommoditiesValue = 0;

        for( int i = 1 ; i < 9 ; i++){
            ownedCommoditiesValue = ownedCommoditiesValue + this.portfolioQuantity[i] * this.currentLobby.commodities[i].getCurrentPrice();
        }

        double equity = cash + ownedCommoditiesValue;

        setEquity(equity);
    }
}
