package server;

public class News {

    private String name;
    private boolean rumour;   //Configure if there will a rumour before the news
    private double positiveProbability;
    private double positiveImpactMultiplier;
    private double negativeImpactMultiplier;
    private double instantPriceChangeMultiplier;
    private int commodityImpacted;
    private int rumourTime;

    public News(String name, boolean rumour, double positiveProbability, double positiveImpactMultiplier, double negativeImpactMultiplier, int commodityImpacted , double instantEffect, int time) {
        this.name = name;
        this.rumour = rumour;
        this.positiveProbability = positiveProbability;
        this.positiveImpactMultiplier = positiveImpactMultiplier;
        this.negativeImpactMultiplier = negativeImpactMultiplier;
        this.commodityImpacted = commodityImpacted;
        this.instantPriceChangeMultiplier = instantEffect;
        this.rumourTime = time;
    }

    public String getName() {
        return name;
    }

    public boolean isRumour() {
        return rumour;
    }

    public double getPositiveProbability() {
        return positiveProbability;
    }

    public double getPositiveImpactMultipler() {
        return positiveImpactMultiplier;
    }

    public double getNegativeImpactMultipler() {
        return negativeImpactMultiplier;
    }

    public int getCommodityImpacted() {
        return commodityImpacted;
    }

    public double getInstantPriceChangeMultiplier() {
        return instantPriceChangeMultiplier;
    }

    public int getRumourTime() {
        return rumourTime;
    }
}
