package sample;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import application.ClientAppThread;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

import static javafx.scene.paint.Color.*;

/**
 * FXML Controller class
 * @ Kam Bansal
 */
public class ScreenController3 implements Initializable, ScreenInterface {


    ScreenMapping mainContainer;
    int xAxisZoomView = 3; //1 is daily


    XYChart.Series<Number , Number > goldSeries = new XYChart.Series<>();
    XYChart.Series<Number , Number > oilSeries = new XYChart.Series<>();
    XYChart.Series<Number , Number > soybeanSeries = new XYChart.Series<>();
    XYChart.Series<Number , Number > sugarSeries = new XYChart.Series<>();
    XYChart.Series<Number , Number > microsoftSeries = new XYChart.Series<>();
    XYChart.Series<Number , Number > jpmorganSeries = new XYChart.Series<>();
    XYChart.Series<Number , Number > royaldutchSeries = new XYChart.Series<>();
    XYChart.Series<Number , Number > teslaSeries = new XYChart.Series<>();

    private ScheduledExecutorService scheduledExecutorService;
    final int WINDOW_SIZE = 100;

    private boolean canIBuyOrSell = true;
    private boolean inEndScreen = false;
    private boolean inEndScreenInitialized = false;
    private boolean isBuyOrSellPressed = false;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("1");

        commoditiesSelection.setExpanded(false);
        selection.setExpandedPane(commoditiesSelection);
        gold.setTextFill(DEEPSKYBLUE);
        quantityDealField.setText("1");

        fundsField.setText("1000000");
        pnlField.setText("0");
        balanceField.setText("1000000");


        initalizeAllCharts();
        hideAllCharts();

        titleGraph.setText("Gold");
        goldLineChart.setVisible(true);

        leaderboardText.getItems().add("Hello");


        placeDealButton.setVisible(false);
        dealMessage.setVisible(false);
        quantityOrderField.setVisible(false);



        runGUIUpdaterThread();
    }

    public void setScreenParent(ScreenMapping screenParent) {
        mainContainer = screenParent;
    }

    @FXML
    private Accordion selection;

    @FXML
    private TitledPane commoditiesSelection;


    @FXML
    private Button yearlyTA;
    @FXML
    private void clickYearlyTA(ActionEvent event) {

        xAxisZoomView = 3;

    }
    @FXML
    private Button monthlyTA;
    @FXML
    private void clickMonthlyTA(ActionEvent event) {

        xAxisZoomView = 2;

    }

    @FXML
    private Button dailyTAn;
    @FXML
    private void clickDailyTA(ActionEvent event) {

        xAxisZoomView = 1;

    }



    @FXML
    LineChart<Number, Number> goldLineChart;
    @FXML
    private NumberAxis goldLineChartXAxis ;
    @FXML
    private NumberAxis goldLineChartYAxis ;


    @FXML
    LineChart<Number, Number> oilLineChart;
    @FXML
    private NumberAxis oilLineChartXAxis ;
    @FXML
    private NumberAxis oilLineChartYAxis ;

    @FXML
    LineChart<Number, Number> soybeanLineChart;
    @FXML
    private NumberAxis soybeanLineChartXAxis ;
    @FXML
    private NumberAxis soybeanLineChartYAxis ;

    @FXML
    LineChart<Number, Number> sugarLineChart;
    @FXML
    private NumberAxis sugarLineChartXAxis ;
    @FXML
    private NumberAxis sugarLineChartYAxis ;

    @FXML
    LineChart<Number, Number> microsoftLineChart;
    @FXML
    private NumberAxis microsoftLineChartXAxis ;
    @FXML
    private NumberAxis microsoftLineChartYAxis ;

    @FXML
    LineChart<Number, Number> jpMorganLineChart;
    @FXML
    private NumberAxis jpMorganLineChartXAxis ;
    @FXML
    private NumberAxis jpMorganLineChartYAxis ;

    @FXML
    LineChart<Number, Number> royalDutchLineChart;
    @FXML
    private NumberAxis royalDutchLineChartXAxis ;
    @FXML
    private NumberAxis royalDutchLineChartYAxis ;

    @FXML
    LineChart<Number, Number> teslaLineChart;
    @FXML
    private NumberAxis teslaLineChartXAxis ;
    @FXML
    private NumberAxis teslaLineChartYAxis ;

    @FXML
    private Label titleGraph;



    @FXML
    private Label gold;
    @FXML
    private void clickGoldLabel() {

        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("1");
        hideAllCharts();
        goldLineChart.setVisible(true);
        allLabelsWhite();
        gold.setTextFill(DEEPSKYBLUE);

    }
    @FXML
    private Label oil;
    @FXML
    private void clickOilLabel() {

        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("2");
        hideAllCharts();
        oilLineChart.setVisible(true);
        allLabelsWhite();
        oil.setTextFill(DEEPSKYBLUE);

    }
    @FXML
    private Label soybean;
    @FXML
    private void clickSoybeanLabel() {

        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("3");
        hideAllCharts();
        soybeanLineChart.setVisible(true);
        allLabelsWhite();
        soybean.setTextFill(DEEPSKYBLUE);

    }


    @FXML
    private Label sugar;
    @FXML
    private void clickSugarLabel() {

        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("4");
        hideAllCharts();
        sugarLineChart.setVisible(true);
        allLabelsWhite();
        sugar.setTextFill(DEEPSKYBLUE);

    }


    @FXML
    private Label microsoft;
    @FXML
    private void clickMicrosftLabel() {

        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("5");
        hideAllCharts();
        microsoftLineChart.setVisible(true);
        allLabelsWhite();
        microsoft.setTextFill(DEEPSKYBLUE);
    }

    @FXML
    private Label jpmorgan;
    @FXML
    private void clickJPMLabel() {
        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("6");
        hideAllCharts();
        jpMorganLineChart.setVisible(true);
        allLabelsWhite();
        jpmorgan.setTextFill(DEEPSKYBLUE);

    }

    @FXML
    private Label royaldutch;
    @FXML
    private void clickRDLabel() {
        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("7");
        hideAllCharts();
        royalDutchLineChart.setVisible(true);
        allLabelsWhite();
        royaldutch.setTextFill(DEEPSKYBLUE);

    }

    @FXML
    private Label tesla;
    @FXML
    private void clickTeslaLabel() {
        MainScreen.myBackend.clientThread.setbuyOrSellCommodityID("8");
        hideAllCharts();
        teslaLineChart.setVisible(true);
        allLabelsWhite();
        tesla.setTextFill(DEEPSKYBLUE);

    }

    @FXML
    private ListView newsList;

    @FXML
    private TitledPane leaderboard;
    @FXML
    private ListView leaderboardText;

    @FXML
    private ListView historyField;

    @FXML
    private ListView portfolioText;

    @FXML
    private TextField quantityOrderField;



    public void calculatePurchaseCostNew(){

        //NOT RETRIEVING TEXT EACH TIME
        String quantity = quantityDealField.getText();
//        System.out.println(quantity);
        Integer quantityInt = Integer.valueOf(quantity);
//        System.out.println(quantityInt);


       // int quantityInt = 10;

        Double priceOfGold = MainScreen.myBackend.clientThread.prices[1];
        Integer integerPriceOfGold = priceOfGold.intValue();
        // System.out.println(integerPriceOfGold);

        Integer totalCost = quantityInt * integerPriceOfGold;
        String totalCostString = Integer.toString(totalCost);
        // System.out.println(totalCost);

        dealPrice.setText(totalCostString);
    }

    @FXML
    private TextField dealPrice;

    @FXML
    private TextField time;





    @FXML
    private Button sellDealButton;


    @FXML
    private Button buyDealButton;

    @FXML
    private TextField fundsField;


    @FXML
    private TextField pnlField;


    @FXML
    private TextField balanceField;

    @FXML
    private TitledPane equitiesSelection;

    @FXML
    private TitledPane position;

    @FXML
    private TextField priceField;

    @FXML
    private TextField perChangeField;

    @FXML
    private TextField highPriceField;

    @FXML
    private TextField lowPriceField;

    @FXML
    private TextField sellPrice;

    @FXML
    private TextField buyPrice;

    @FXML
    private Button placeDealButton;

    @FXML
    private Label dealMessage;

    @FXML
    private Button sellOrderButton;

    @FXML
    private Button buyOrderButton;

    @FXML
    private TextField sellOrderPrice;

    @FXML
    private TextField buyOrderPrice;

    @FXML
    private Button placeOrder;

    @FXML
    private TextField quantityDealField;

    @FXML
    private Label dealOrderMessage;

    @FXML
    private TextField priceOrderField;

    @FXML
    private Button alertSellButton;

    @FXML
    private Button alertBuyButton;

    @FXML
    private Button sellDeal;

    @FXML
    private Button buyDeal;

    @FXML
    private TextField alertLevelField;

    @FXML
    private Tab AlertTab;

    @FXML
    private Tab OrderTab;


    @FXML
    private Button setAlertButton;

    public void sellButtonDeal(ActionEvent event) {
        MainScreen.myBackend.clientThread.setbuyOrSellCommand("2");
        sellDeal.setStyle("-fx-background-color: red;");
        sellDeal.setTextFill(WHITE);
        buyDeal.setStyle("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;");
        buyDeal.setTextFill(GRAY);



        placeDealButton.setVisible(true);

    }

    public void buyButtonDeal(ActionEvent event) {
        MainScreen.myBackend.clientThread.setbuyOrSellCommand("1");
        buyDeal.setStyle("-fx-background-color: blue;");
        buyDeal.setTextFill(WHITE);
        sellDeal.setStyle(("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;"));
        sellDeal.setTextFill(GRAY);

        placeDealButton.setVisible(true);

    }

    public void buyOrderButton(ActionEvent event) {
        MainScreen.myBackend.clientThread.setbuyOrSellCommand("1");
        buyOrderButton.setStyle("-fx-background-color: blue;");
        buyOrderButton.setTextFill(WHITE);
        sellOrderButton.setStyle(("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;"));
        sellOrderButton.setTextFill(GRAY);

    }

    public void sellOrderButton(ActionEvent event) {
        MainScreen.myBackend.clientThread.setbuyOrSellCommand("2");
        sellOrderButton.setStyle("-fx-background-color: red;");
        sellOrderButton.setTextFill(WHITE);
        buyOrderButton.setStyle("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;");
        buyOrderButton.setTextFill(GRAY);

    }

    public void buyAlertButton(ActionEvent event) {
        MainScreen.myBackend.clientThread.setbuyOrSellCommand("1");
        alertBuyButton.setStyle("-fx-background-color: blue;");
        alertBuyButton.setTextFill(WHITE);
        alertSellButton.setStyle(("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;"));
        alertSellButton.setTextFill(GRAY);

    }

    public void sellAlertButton(ActionEvent mouseEvent) {
        MainScreen.myBackend.clientThread.setbuyOrSellCommand("2");
        alertSellButton.setStyle("-fx-background-color: red;");
        alertSellButton.setTextFill(WHITE);
        alertBuyButton.setStyle("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;");
        alertBuyButton.setTextFill(GRAY);

    }

    public void dealButton(ActionEvent event) {

        if(canIBuyOrSell){
            String commodity = MainScreen.myBackend.clientThread.buyOrSellCommodityID;
            int commodityInt = Integer.parseInt(commodity);
            int buyOrSellQuantityInt = Integer.parseInt(quantityDealField.getText());
            //String buyOrSellQuantity = MainScreen.myBackend.clientThread.buyOrSellQuantity;
            //int buyOrSellQuantityInt = Integer.parseInt(buyOrSellQuantity);

            if(MainScreen.myBackend.clientThread.buyOrSellCommand.equals("2")){

                if(buyOrSellQuantityInt <= MainScreen.myBackend.clientThread.commoditiesOwnedquantity[commodityInt]){
                    int value = Integer.parseInt(quantityDealField.getText());
                    // System.out.println("Quantity value to deal: " + value);
                    String s = String.valueOf(value);
                    MainScreen.myBackend.clientThread.setbuyOrSellQuantity(s);
                    MainScreen.myBackend.clientThread.buyOrSellSendToServerStringArrayCompose();
                    canIBuyOrSell = false;
                }else{
                    dealMessage.setText("You do not own sufficient quantity.");
                    dealMessage.setVisible(true);
                }

            }
            if(MainScreen.myBackend.clientThread.buyOrSellCommand.equals("1")){
                double cashAvaliable = MainScreen.myBackend.clientThread.cashAvailable;
                double costOfPurchase = buyOrSellQuantityInt * MainScreen.myBackend.clientThread.prices[commodityInt];

                if(cashAvaliable >= costOfPurchase){
                    int value = Integer.parseInt(quantityDealField.getText());
                    // System.out.println("Quantity value to deal: " + value);
                    String s = String.valueOf(value);
                    MainScreen.myBackend.clientThread.setbuyOrSellQuantity(s);
                    MainScreen.myBackend.clientThread.buyOrSellSendToServerStringArrayCompose();
                    canIBuyOrSell = false;
                }else{
                    dealMessage.setText("You do not have enough cash available.");
                    dealMessage.setVisible(true);
                }


            }

        }else{
            dealMessage.setText("Stock Market regulations prohibit more than 1 purchase or sale per second");
            dealMessage.setVisible(true);
        }
    }
    @FXML
    public void textFieldEnterInt(MouseEvent event) {

        quantityDealField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d*")) {
                    quantityDealField.setText(newValue);
                } else {
                    quantityDealField.setText(oldValue);
                }
            }
        });

    }

    public void placeOrderButton(ActionEvent event) {

        int valueOrder = Integer.parseInt(quantityOrderField.getText());
        double valuePrice = Double.parseDouble(priceOrderField.getText());
        // need to add a value here for price to be captured for price separately if we
        //incorporate this feature (order to buy or sell) as price order is not being
        //captured i..e only get three responses 1 1 15 for example

        //System.out.println("Quantity value of order: " + valueOrder);
        //System.out.println("Price set of order: " +valuePrice);
        String s = String.valueOf(valueOrder);
        MainScreen.myBackend.clientThread.setbuyOrSellQuantity(s);
        MainScreen.myBackend.clientThread.buyOrSellSendToServerStringArrayCompose();
    }

    public void setAlertButton(ActionEvent event) {

        double valuePriceAlert = Double.parseDouble(alertLevelField.getText());
        System.out.println("Alert level set at: " + valuePriceAlert);
        String s = String.valueOf(valuePriceAlert);
        MainScreen.myBackend.clientThread.setbuyOrSellQuantity(s);
        MainScreen.myBackend.clientThread.buyOrSellSendToServerStringArrayCompose();
    }

    public static long getGameTime(Date startTime, Date currentTime, TimeUnit timeUnit) {
        long diffInMilli = currentTime.getTime() - startTime.getTime();
        return timeUnit.convert(diffInMilli,TimeUnit.MILLISECONDS);
    }

    public void runGUIUpdaterThread(){



        // this is used to display time in HH:mm:ss format
        final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10
            Integer random = ThreadLocalRandom.current().nextInt(10);



            // Update the chart
            Platform.runLater(() -> {

                ClientAppThread myParentThread = mainContainer.mainScreen.myBackend.clientThread;



                if(myParentThread.isInGame == 1){

                    //Update cash avaliable
                    DecimalFormat numbrFormat = new DecimalFormat("#.00");
                    double rawFundsAvaliable = myParentThread.cashAvailable;
                    String fundsAvaliable = String.valueOf( numbrFormat.format(rawFundsAvaliable));
                    fundsField.setText(fundsAvaliable);

                    //Game time
                    long gameTime = getGameTime( myParentThread.gameStartTime, new Date(),TimeUnit.SECONDS);

                    //Set Game time
                    int GUITime = Math.toIntExact(gameTime);
                    int minutes = (GUITime % 3600) / 60;
                    int seconds = GUITime % 60;
                    String GUITimeString = String.format("%02d:%02d", minutes, seconds);
                    time.setText(GUITimeString);
                    xAxisZoomViewSetter(GUITime);

                    String xAxisDateStringPlot = xAxisDateString(GUITime);

                    //Plot data
                    if(GUITime % 2 == 0){

                        for(int i = 1 ; i < 9 ; i++){
                            Double price = myParentThread.prices[i];
                            Integer integerPrice = price.intValue();
                            String stringPrice = Integer.toString(integerPrice);

//                        if(myParentThread.buyOrSellCommodityID.equals("1")){
//                            priceField.setText(stringPrice);
//                        }

                            //Get Timestamp
                            int timestamp = myParentThread.latestPriceUpdate[i];
                            String stringTimeStamp = Integer.toString(timestamp);
                            String dateFormatXAxis = xAxisDateString(timestamp);

                            if(myParentThread.gameCommodities[i].equals("Gold")){
                                goldSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("1")){
                                    priceField.setText(stringPrice);
                                }
                            }else if(myParentThread.gameCommodities[i].equals("Oil")){
                                oilSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("2")){
                                    priceField.setText(stringPrice);
                                }
                            }else if(myParentThread.gameCommodities[i].equals("Soybean")){
                                soybeanSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("3")){
                                    priceField.setText(stringPrice);
                                }
                            }else if(myParentThread.gameCommodities[i].equals("Sugar")){
                                sugarSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("4")){
                                    priceField.setText(stringPrice);
                                }
                            }else if(myParentThread.gameCommodities[i].equals("Microsoft")){
                                microsoftSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("5")){
                                    priceField.setText(stringPrice);
                                }
                            }else if(myParentThread.gameCommodities[i].equals("JPMorgan")){
                                jpmorganSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("6")){
                                    priceField.setText(stringPrice);
                                }
                            }else if(myParentThread.gameCommodities[i].equals("RoyalDutch")){
                                royaldutchSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("7")){
                                    priceField.setText(stringPrice);
                                }
                            }else if(myParentThread.gameCommodities[i].equals("Tesla")){
                                teslaSeries.getData().add(new XYChart.Data<>(GUITime, price));
                                if(myParentThread.buyOrSellCommodityID.equals("8")){
                                    priceField.setText(stringPrice);
                                }
                            }


                        }

                    }


                    if (GUITime == 600) {
                        myParentThread.isInGame = 0;
                        mainContainer.setScreen(MainScreen.screenEnd);

                        inEndScreen = true;
                    }



                    //dealPrice.setText("Test calculator");
                    calculatePurchaseCostNew();

                    double portfolioValue = 0;
                    //Calculate PnL
                    for( int i = 1 ; i < 5 ; i++){
                        portfolioValue = portfolioValue + (myParentThread.commoditiesOwnedquantity[i] * myParentThread.prices[i]);

                    }
                    double equity = portfolioValue + rawFundsAvaliable;

                    double pnL = equity - 1000000;

                    String equityString = String.valueOf( numbrFormat.format(equity));
                    String pnLString = String.valueOf( numbrFormat.format(pnL));
                    balanceField.setText(equityString);
                    pnlField.setText(pnLString);
                    //System.out.println("PNL: " + portfolioValue);

                    double percentageChange = (((myParentThread.prices[1] / myParentThread.pricesMinusOneDay[1]) - 1) * 100.0);
                    String percentageChangeString = String.valueOf( numbrFormat.format(percentageChange));
                    perChangeField.setText(percentageChangeString);

                    if(myParentThread.rumourStringReady){
                        newsList.getItems().add(myParentThread.rumourString);

                        newsList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                            @Override
                            public ListCell<String> call(final ListView<String> list) {
                                return new ListCell<String>() {
                                    {
                                        Text text = new Text();
                                        text.wrappingWidthProperty().bind(list.widthProperty().subtract(15));
                                        text.textProperty().bind(itemProperty());

                                        setPrefWidth(0);
                                        setGraphic(text);
                                    }
                                };
                            }
                        });
                        myParentThread.rumourString = null;
                        myParentThread.rumourStringReady = false;
                    }


                    leaderboardText.getItems().clear();
                    leaderboardText.getItems().add(myParentThread.playersLeaderboard);

                    if(myParentThread.historyStringReady){
                        historyField.getItems().add(myParentThread.historyString);
                        myParentThread.historyStringReady = false;
                    }

                    portfolioText.getItems().clear();
                    portfolioText.getItems().add(myParentThread.portfolioGUI);

                    canIBuyOrSell = true;

                }

                if(inEndScreen){
                    if(!inEndScreenInitialized){
                        leaderBoard leaderController = (leaderBoard) mainContainer.getController("leaderBoard");
                        leaderController.updateLeaderBoard();
                        inEndScreenInitialized = true;
                    }

                }



            });
        }, 0, 1, TimeUnit.SECONDS);


    }



    private String xAxisDateString(int gameTime){

        String dateString = "";

        if(gameTime <= 31){
            dateString = "Jan " + gameTime;
        }else if(gameTime <= 59){
            dateString = "Feb " + (gameTime - 31);
        }else if(gameTime <= 90){
            dateString = "Mar " + (gameTime - 59);
        }else if(gameTime <= 120){
            dateString = "Apr " + (gameTime - 90);
        }else if(gameTime <= 151){
            dateString = "May " + (gameTime - 120);
        }else if(gameTime <= 181){
            dateString = "Jun " + (gameTime - 151);
        }else if(gameTime <= 212){
            dateString = "Jul " + (gameTime - 181);
        }else if(gameTime <= 243){
            dateString = "Aug " + (gameTime - 212);
        }else if(gameTime <= 273){
            dateString = "Sep " + (gameTime - 243);
        }else if(gameTime <= 304){
            dateString = "Oct " + (gameTime - 273);
        }else if(gameTime <= 334){
            dateString = "Nov " + (gameTime - 304);
        }else if(gameTime > 335){
            dateString = "Dec " + (gameTime - 334);
        }

        return dateString;

    }

    private void xAxisZoomViewSetter(int GUITime){

        String whichChart = MainScreen.myBackend.clientThread.buyOrSellCommodityID;
        NumberAxis xAxis = null;

        if(whichChart.equals("1")){
            xAxis = goldLineChartXAxis;
        }else if(whichChart.equals("2")){
            xAxis = oilLineChartXAxis;
        }else if(whichChart.equals("3")){
            xAxis = soybeanLineChartXAxis;
        }else if(whichChart.equals("4")){
            xAxis = sugarLineChartXAxis;
        }else if(whichChart.equals("5")){
            xAxis = microsoftLineChartXAxis;
        }else if(whichChart.equals("6")){
            xAxis = jpMorganLineChartXAxis;
        }else if(whichChart.equals("7")){
            xAxis = royalDutchLineChartXAxis;
        }else if(whichChart.equals("8")){
            xAxis = teslaLineChartXAxis;
        }

        if(GUITime % 2 == 0){

            if(xAxisZoomView == 1){

                xAxis.setUpperBound(GUITime + 15);

                if((GUITime - 15) >= 0){
                    xAxis.setLowerBound(GUITime - 15);
                }else{
                    xAxis.setUpperBound(15);
                    xAxis.setLowerBound(0);
                }
            }

            if(xAxisZoomView == 2){

                xAxis.setUpperBound(GUITime + 45);

                if((GUITime - 45) >= 0){
                    xAxis.setLowerBound(GUITime - 45);
                }else{
                    xAxis.setUpperBound(45);
                    xAxis.setLowerBound(0);
                }
            }

            if(xAxisZoomView == 3){
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(365);
            }

        }



    }


    public void initalizeAllCharts(){

        //Setup Charts
        goldLineChart.getData().add(goldSeries);
        goldLineChart.setLegendVisible(false);
        goldLineChart.setCreateSymbols(false);
        goldLineChartYAxis.setLabel("Price");
        goldLineChartYAxis.setAutoRanging(true);
        goldLineChartYAxis.setLowerBound(500);
        goldLineChartYAxis.setUpperBound(1500);
        goldLineChartXAxis.setLabel("Day");
        goldLineChartXAxis.setLowerBound(0);
        goldLineChartXAxis.setUpperBound(35);
        goldLineChartXAxis.setAutoRanging(false);

        oilLineChart.getData().add(oilSeries);
        oilLineChart.setLegendVisible(false);
        oilLineChart.setCreateSymbols(false);
        oilLineChartYAxis.setLabel("Price");
        oilLineChartYAxis.setAutoRanging(true);
        oilLineChartYAxis.setLowerBound(500);
        oilLineChartYAxis.setUpperBound(1500);
        oilLineChartXAxis.setLabel("Day");
        oilLineChartXAxis.setLowerBound(0);
        oilLineChartXAxis.setUpperBound(35);
        oilLineChartXAxis.setAutoRanging(false);

        soybeanLineChart.getData().add(soybeanSeries);
        soybeanLineChart.setLegendVisible(false);
        soybeanLineChart.setCreateSymbols(false);
        soybeanLineChartYAxis.setLabel("Price");
        soybeanLineChartYAxis.setAutoRanging(true);
        soybeanLineChartYAxis.setLowerBound(500);
        soybeanLineChartYAxis.setUpperBound(1500);
        soybeanLineChartXAxis.setLabel("Day");
        soybeanLineChartXAxis.setLowerBound(0);
        soybeanLineChartXAxis.setUpperBound(35);
        soybeanLineChartXAxis.setAutoRanging(false);

        sugarLineChart.getData().add(sugarSeries);
        sugarLineChart.setLegendVisible(false);
        sugarLineChart.setCreateSymbols(false);
        sugarLineChartYAxis.setLabel("Price");
        sugarLineChartYAxis.setAutoRanging(true);
        sugarLineChartYAxis.setLowerBound(500);
        sugarLineChartYAxis.setUpperBound(1500);
        sugarLineChartXAxis.setLabel("Day");
        sugarLineChartXAxis.setLowerBound(0);
        sugarLineChartXAxis.setUpperBound(35);
        sugarLineChartXAxis.setAutoRanging(false);

        microsoftLineChart.getData().add(microsoftSeries);
        microsoftLineChart.setLegendVisible(false);
        microsoftLineChart.setCreateSymbols(false);
        microsoftLineChartYAxis.setLabel("Price");
        microsoftLineChartYAxis.setAutoRanging(true);
        microsoftLineChartYAxis.setLowerBound(500);
        microsoftLineChartYAxis.setUpperBound(1500);
        microsoftLineChartXAxis.setLabel("Day");
        microsoftLineChartXAxis.setLowerBound(0);
        microsoftLineChartXAxis.setUpperBound(35);
        microsoftLineChartXAxis.setAutoRanging(false);

        jpMorganLineChart.getData().add(jpmorganSeries);
        jpMorganLineChart.setLegendVisible(false);
        jpMorganLineChart.setCreateSymbols(false);
        jpMorganLineChartYAxis.setLabel("Price");
        jpMorganLineChartYAxis.setAutoRanging(true);
        jpMorganLineChartYAxis.setLowerBound(500);
        jpMorganLineChartYAxis.setUpperBound(1500);
        jpMorganLineChartXAxis.setLabel("Day");
        jpMorganLineChartXAxis.setLowerBound(0);
        jpMorganLineChartXAxis.setUpperBound(35);
        jpMorganLineChartXAxis.setAutoRanging(false);

        royalDutchLineChart.getData().add(royaldutchSeries);
        royalDutchLineChart.setLegendVisible(false);
        royalDutchLineChart.setCreateSymbols(false);
        royalDutchLineChartYAxis.setLabel("Price");
        royalDutchLineChartYAxis.setAutoRanging(true);
        royalDutchLineChartYAxis.setLowerBound(500);
        royalDutchLineChartYAxis.setUpperBound(1500);
        royalDutchLineChartXAxis.setLabel("Day");
        royalDutchLineChartXAxis.setLowerBound(0);
        royalDutchLineChartXAxis.setUpperBound(35);
        royalDutchLineChartXAxis.setAutoRanging(false);

        teslaLineChart.getData().add(teslaSeries);
        teslaLineChart.setLegendVisible(false);
        teslaLineChart.setCreateSymbols(false);
        teslaLineChartYAxis.setLabel("Price");
        teslaLineChartYAxis.setAutoRanging(true);
        teslaLineChartYAxis.setLowerBound(500);
        teslaLineChartYAxis.setUpperBound(1500);
        teslaLineChartXAxis.setLabel("Day");
        teslaLineChartXAxis.setLowerBound(0);
        teslaLineChartXAxis.setUpperBound(35);
        teslaLineChartXAxis.setAutoRanging(false);
    }

    public void hideAllCharts(){
        goldLineChart.setVisible(false);
        oilLineChart.setVisible(false);
        soybeanLineChart.setVisible(false);
        sugarLineChart.setVisible(false);
        goldLineChart.setCreateSymbols(false);
        microsoftLineChart.setVisible(false);
        jpMorganLineChart.setVisible(false);
        royalDutchLineChart.setVisible(false);
        teslaLineChart.setVisible(false);

        placeDealButton.setVisible(false);
        buyDeal.setStyle("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;");
        sellDeal.setStyle("-fx-background-color: #efefef; -fx-border-color: grey; -fx-border-radius: 3;");
        buyDeal.setTextFill(BLACK);
        sellDeal.setTextFill(BLACK);

        dealMessage.setVisible(false);
    }

    public void allLabelsWhite(){
        gold.setTextFill(WHITE);
        oil.setTextFill(WHITE);
        soybean.setTextFill(WHITE);
        sugar.setTextFill(WHITE);
        microsoft.setTextFill(WHITE);
        jpmorgan.setTextFill(WHITE);
        royaldutch.setTextFill(WHITE);
        tesla.setTextFill(WHITE);
    }


}










