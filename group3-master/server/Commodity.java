package server;

import java.util.Random;

/**
 * @author group3.Anfan
 */
public class Commodity {

    private String name;
    private int itemID;
    private double currentPrice;
    private int quantity;

    private double positiveProbability;
    private double positiveImpactMultipler;
    private double negativeImpactMultipler;
    private int rumourTimeRemaining;


    // Constructor for global Commodity
    // m should be small negative number e.g. -0.005
    public Commodity(String name, int itemID, double price){
        this.name = name;
        this.itemID = itemID;
        this.currentPrice = price;
        this.quantity = 1000000;

        this.positiveProbability = 0.5;
        this.positiveImpactMultipler = 0.01;
        this.negativeImpactMultipler = 0.01;
        this.rumourTimeRemaining = 0;

        System.out.printf("Commodity %s created. Starting price %f, starting quantity %d%n",
                name, this.getCurrentPrice(), quantity);
    }



    public void add(Commodity otherItem){
        this.quantity = this.quantity + otherItem.quantity;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPositiveProbability() {
        return positiveProbability;
    }

    public double getPositiveImpactMultipler() {
        return positiveImpactMultipler;
    }

    public double getNegativeImpactMultipler() {
        return negativeImpactMultipler;
    }

    public void setPositiveProbability(double positiveProbability) {
        this.positiveProbability = positiveProbability;
    }

    public void setPositiveImpactMultipler(double positiveImpactMultipler) {
        this.positiveImpactMultipler = positiveImpactMultipler;
    }

    public void setNegativeImpactMultipler(double negativeImpactMultipler) {
        this.negativeImpactMultipler = negativeImpactMultipler;
    }

    public int getRumourTimeRemaining() {
        return rumourTimeRemaining;
    }

    public void setRumourTimeRemaining(int rumourTimeRemaining) {
        this.rumourTimeRemaining = rumourTimeRemaining;
    }

    /**
     * Set the trend of price through probability and its speed through the impact
     * @param probability
     * @param negativeImpact
     */
    public void setNewsImpact(double probability , double positiveImpact , double negativeImpact){
        this.positiveProbability = probability;
        this.positiveImpactMultipler = positiveImpact;
        this.negativeImpactMultipler = negativeImpact;
    }

    /**
     * Class to return the multipler on the current price for server updates
     * @return
     */
    public double newsImpact(){

        double randomValueImpact;

        //Create a random number between 1 and 0
        Random rand = new Random();
        double positiveOrNegative = rand.nextFloat();


        if(positiveOrNegative <= this.positiveProbability){

            randomValueImpact = (this.positiveImpactMultipler) * rand.nextDouble();


        }else{

            randomValueImpact = -((this.negativeImpactMultipler) * rand.nextDouble());



        }

        //System.out.println(getName() + " has had a price change multiplier of : " + randomValueImpact);
        return randomValueImpact;
    }

}
