package server;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;

/**
 * This class handles the service of lobbies. Both a client creating one and clients joining one
 * @author Thomas Baker
 */
public class ActiveLobby extends Thread {

	private int lobbyID;
	private int lobbySize;
	private boolean inGame;
	public ArrayList<TradingAccount> players;
	private ArrayList<ServerThread> playerServerThread;
	public ArrayList<PurchaseOrSaleReceipt> transactionHistory = new ArrayList<>();

	private int key;
	public Commodity[] commodities;
	public ArrayList<News> newsList;
	private Date startTime;
	public long gameTimer;


	// Constructor for lobbby. inGame is set to false as a default
	public ActiveLobby(int lobbyID){
		this.lobbyID = lobbyID;
		this.lobbySize = 1;
		this.inGame = false;
		this.players = new ArrayList<>();
		this.playerServerThread = new ArrayList<>();
		this.key = createKey();
		this.commodities = new Commodity[10];
		commodities[1] = new Commodity("Gold", 1 , 1000);
		commodities[2] = new Commodity("Oil", 2 , 60);
		commodities[3] = new Commodity("Soybeans", 3 , 920);
		commodities[4] = new Commodity("Sugar", 4 , 100);
		commodities[5] = new Commodity("Microsoft", 5 , 120);
		commodities[6] = new Commodity("JPMorgan", 6 , 100);
		commodities[7] = new Commodity("RoyalDutch", 7 , 30);
		commodities[8] = new Commodity("Tesla", 8 , 260);
		this.startTime = null;
		this.newsList = new ArrayList<>();

		News gold1 = new News("Billington Post: Fort Knox break in, 1/3 of worlds Gold stolen" , false , 0, 0, 0, 1, 1.5, 0);
		News gold2 = new News("Fox News: FOMC meeting majority members speak hawkishly. Seeing signs of inflation creeping higher" , true , 0.7, 0.1, 0.02, 1, 0 , 5);
		News gold3 = new News("CNBC: Trump sends top officials to Beijing to progress China trade talks. Expect immediate benefits to economy", true , 0.2, 0.02, 0.15, 1, 0, 8);
		News gold4 = new News("Washington Post: Apparently Mueller got it wrong. New evidence provides rumour Trump to step down on omitted findings" , false , 0.0, 0, 0, 1, 1.2 , 0);
		News gold5 = new News("Twitter: Trump tweets \"Why doesn’t the FED get it.. The dollar is way too strong still. Get with the program FED!\"" , true, 0.4, 0.08, 0.2, 1, 0, 10);
		News gold6 = new News("FOX News: China agrees to reduce tarrifs on semiconductors" , false , 0, 0, 0, 1, 0.7 , 0);
		News gold7 = new News("CNBC: Yield curve inverts for the first time" , true , 0.4, 0.015, 0.12, 1, 0, 15);
		News gold8 = new News("Washington Post: Jamie Diner \"The next few months FED data points are going to be crucial to determine if we are near a recessionn\"" , true , 0.45, 0.05, 0.1, 1, 0 , 5);
		News gold9 = new News("Zerohedge: Israeli Airstrikes Rock Gaza, Target Hamas Command" , true , 0.7, 0.2, 0.1, 1, 0, 6);
		News gold10 = new News("CNBC: Bond market says not only is a recession coming, but the FED will cut interest rates to stop it" , true , 0.8, 0.2, 0.02, 1, 0 , 10);

		newsList.add(gold1);
		newsList.add(gold2);
		newsList.add(gold3);
		newsList.add(gold4);
		newsList.add(gold5);
		newsList.add(gold6);
		newsList.add(gold7);
		newsList.add(gold8);
		newsList.add(gold9);
		newsList.add(gold10);

		News WTI1 = new News("Official: Weekly Inventory shows an usual large build for this season" , true , 0.3, 0.04, 0.2, 2, 0, 10);
		News WTI2 = new News("Bloomberg: Largest refinery in US shuts down for prolonged maintenance" , true , 0.6, 0.2, 0.04, 2, 0, 10);

		newsList.add(WTI1);
		newsList.add(WTI2);

		News soybean1 = new News("Health Weekly: Nutrienists reveal the health benefits of soyabean" , true , 0.7, 0.2, 0.05, 3, 1, 8);
		News soybean2 = new News("MSNBC: Soyabean harvest in the US reaches low due to Tyroid disease breakout " , true , 0.6, 0.25, 0.02, 3, 0, 5);
		News soybean3 = new News("BBC: Trump sends top officials to Beijing to progress China trade talks" , true , 0.6, 0.15, 0.05, 3, 0, 4);
		News soybean4 = new News("Reuters: African swine fever has spread rapidly throughout China" , true , 0.6, 0.2, 0.04, 3, 0, 10);
		News soybean5 = new News("Reuters: new vaccination  has meant that swine fever is erradicated" , false , 0, 0, 0, 3, 0.7, 0);
		newsList.add(soybean1);
		newsList.add(soybean2);
		newsList.add(soybean3);
		newsList.add(soybean4);
		newsList.add(soybean5);

		News microsoft1 = new News("Twitter: Musk Tweets I wouldn’t be surprised that a big Tech company takes a look at Tesla in the not so distance future" , true , 0.2, 0.4, 0, 5, 1, 10);
		News microsoft2 = new News("Digital Post: Microsoft leaks images of Surface 5 with 4 days battery life" , true , 0.4, 0.5, 0.04, 5, 1, 10);
		News microsoft3 = new News("Digital Post: Bill Gates believes that everyone should read at least one book a month" , true , 0.0, 0.0, 0.04, 5, 1, 10);
		News microsoft4 = new News("CNBC: Bill Gates thinks that Tesla is an interesting company and has a lot of admiration for Musk " , true , 0.0, 0.0, 0.00, 5, 1, 10);
		News microsoft5 = new News("Bank of America: Goes from Neutral to Overweight on better server business at Azure " , true , 0.4, 0.5, 0.00, 5, 1.1, 10);

		newsList.add(microsoft1);
		newsList.add(microsoft2);
		newsList.add(microsoft3);
		newsList.add(microsoft4);
		newsList.add(microsoft5);


		News jpm1 = new News("CNBC: Berkshire Hathaway doubles down on its stake in JPM" , false , 0.2, 0.4, 0, 6, 1.1, 10);
		News jpm2 = new News("StreetInsider: StreetInsider: JPM eyeing a takeover for UBS" , true , 0, 0.02, 0.4, 6, 1.1, 10);
		News jpm3 = new News("Official: results eps 0.57 vs street 0.52" , false  , 0.3, 0.3, 0.4, 6, 1.2, 30);
		News jpm4 = new News("Official: Morgan Stanley reports numbers below street 0.5 vs 0.6" , false , 0.04, 0.02, 0.25, 6, 1.1, 5);
		News jpm5 = new News("UBS: UBS downgrades the stock to underweight vs overweight. Sites more exposed to consumer spending into a recession" , false , 0, 0.02, 0.5, 6, 1.2, 10);
		newsList.add(jpm1);
		newsList.add(jpm2);
		newsList.add(jpm3);
		newsList.add(jpm4);
		newsList.add(jpm5);

		News RD1 = new News("Official: Results out.. Eps 0.8 vs street 1.2" , false , 0.02, 0.07, 0.2, 7, 1.2, 10);
		News RD2 = new News("Official: Conference call says that reason for the miss was due to final payment to Gulf. Raises guidance/" , false  , 0.6, 0.3, 0.102, 7, 1.3, 10);
		News RD3 = new News("Official: Royal Dutch to spin out their natural gas business - IPO to come" , false  , 0.6, 0.2, 0.402, 7, 1.3, 10);
		News RD4 = new News("Fox News: Rumours that Royal Dutch are looking to buy BP " , false  , 0.4, 0.02, 0.4, 7, 1.1, 10);
		News RD5 = new News("Bloomberg: Royal Dutch to buy BP in all stock deal! " , false  , 0.3, 0.1, 0.2, 7, 1.3, 10);
		newsList.add(RD1);
		newsList.add(RD2);
		newsList.add(RD3);
		newsList.add(RD4);
		newsList.add(RD5);

		News tesla1 = new News("Twitter: Musk Tweets \"I would not want to be short ahead of my presentation this week!\"" , true , 0.4, 0.3, 0.4, 8, 1.2, 10);
		News tesla2 = new News("Bloomberg: MUSK to be investigated for latest Tweets" , false  , 0, 0, 0.04, 8, 1, 10);
		News tesla3 = new News("Twitter: Musk Tweets: I wouldn’t be surprised that a big Tech company takes a look at Tesla in the not so distance future" , true  , 0.4, 0.4, 0.04, 8, 1.1, 10);
		News tesla4 = new News("CNBC: Musk to step down as CEO of Tesla!", false  , 0.6, 0.2, 0.04, 8, 1.2, 10);
		News tesla5 = new News("Bloomberg: Musk to IPO solar panel business later in the year", false  , 0.6, 0.2, 0.04, 8, 1.2, 10);
		News tesla6 = new News("Reuters: Elon Musk wins dismissal of lawsuit over Model 3 production", false  , 0, 0, 0.04, 8, 1, 10);
		News tesla7 = new News("TheStreets: Hearing of another fatal crash involving a driver and a passenger in a Model S", true  , 0.2, 0.02, 0.4, 8, 1.2, 10);
		News tesla8 = new News("CNBC: Apple to start making electric cars. Poaches head of Engineering from Teala", false  , 0.2, 0.02, 0.6, 8, 1.2, 10);

		newsList.add(tesla1);
		newsList.add(tesla2);
		newsList.add(tesla3);
		newsList.add(tesla4);
		newsList.add(tesla5);
		newsList.add(tesla6);
		newsList.add(tesla7);
		newsList.add(tesla8);

	}


	public static long getGameTime(Date startTime, Date currentTime, TimeUnit timeUnit) {
		long diffInMilli = currentTime.getTime() - startTime.getTime();
		return timeUnit.convert(diffInMilli,TimeUnit.MILLISECONDS);
	}

	// lobby timer tickets
	// include code for random price fluctuations
	@Override
	public void run(){
		this.inGame = true;
		this.startTime = new Date();

		while (true){

			gameTimer = getGameTime(startTime , new Date() , TimeUnit.SECONDS);



			//long currentTimePassed = elapsedTime();
			if(gameTimer % 2 == 0){
				System.out.println(gameTimer);
				PurchaseOrSaleReceipt newsReceipt = null;
				//System.out.println(currentTimePassed);
				//CALL THE AUTOMATED PRICE UPDATER ON ALL COMMODITIES AND COMBINE INTO A OBJECT ARRAY
				PurchaseOrSaleReceipt[] arraySetter = naturalPriceFluctuations();


				if(gameTimer % 15 == 0){
					newsReceipt = newsEventStart();
				}

				for(ServerThread x: playerServerThread){

					x.setNaturalFluxationArray(arraySetter);
					x.setIsNaturalFluctuationReady(true);

					if(newsReceipt != null){ //% 15 == 0
						x.setNewsReceipt(newsReceipt);
						x.setIsNewsReady(true);
					}
				}



				for(int i = 1 ; i < 9 ; i++){
					int time = commodities[i].getRumourTimeRemaining();
					if(time == 1){
						commodities[i].setPositiveProbability(0.5);
						commodities[i].setPositiveImpactMultipler(0.01);
						commodities[i].setNegativeImpactMultipler(0.01);
					}
					if(time > 0){
						time--;
					}
					commodities[i].setRumourTimeRemaining(time);
				}

				for(TradingAccount x: players){
					x.updateEquity();
				}




				if (gameTimer == 601){
					System.out.println("Time has finished: game over!");
					break;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}


	// move buy code to random lobby
	public synchronized void buy(){
		Commodity gold = commodities[0];
		System.out.println("Old gold quantity: " + gold.getQuantity());
		gold.setQuantity(50);

		System.out.println("Bought. New gold quantity: " + gold.getQuantity());
	}







//	public int elapsedTime(){
//		long elapsedTime = System.currentTimeMillis() + 1000 - startTime;
//		long elapsedSeconds = elapsedTime / 1000;
//		int seconds = Math.toIntExact(elapsedSeconds);
//		return seconds;
//	}

	//
	public int createKey() {
		
		int lobbyKey;
		
		//Create random 6 digit lobby key
		Random rnd = new Random();
		lobbyKey = 100000 + rnd.nextInt(900000);
		System.out.println("Lobby ID: " + getLobbyID() + " Lobby key: " + lobbyKey);
		
		return lobbyKey;
	}
	
	

	public int getLobbyID() {
		return lobbyID;
	}

	public int getLobbySize() {
		return lobbySize;
	}

	public void setLobbySize(int lobbySize) {
		this.lobbySize = lobbySize;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public Commodity[] getCommodities() {
		return commodities;
	}

	public void setCommodities(Commodity[] commodities) {
		this.commodities = commodities;
	}

	// Finding a player in an active lobby
	public TradingAccount getPlayer(String name) {
		for (TradingAccount x: players){
			if (x.getName().equals(name)){
				return x;
			}
		}
		return null;
	}

	// adding a player to an active lobby
	public void addPlayer(TradingAccount player, ServerThread thread) {
		this.players.add(player);
		this.playerServerThread.add(thread);
		this.lobbySize = players.size();
	}

	// removing a player from an existing lobby
	public void removePlayer(TradingAccount player){
		this.players.remove(player);
		this.lobbySize = players.size();
	}


	public int getKey() {
		return key;
	}

	public synchronized PurchaseOrSaleReceipt[] naturalPriceFluctuations(){

		PurchaseOrSaleReceipt[] naturalFluctuationArray = new PurchaseOrSaleReceipt[9];


		for(int i = 1 ; i < 9 ; i++){
			double oldPrice = commodities[i].getCurrentPrice();

			Random r = new Random();

			double newPrice = oldPrice * (1 + commodities[i].newsImpact());
			commodities[i].setCurrentPrice(newPrice);
			//System.out.println("Price of " + commodities[i].getName() + " changed from " + oldPrice + " to " + newPrice);
			String playerInstance = generatePlayersAndEquityString();
			int timeStampInt = toIntExact(gameTimer);
			PurchaseOrSaleReceipt naturalReceipt = new PurchaseOrSaleReceipt( "Auto Server Change" , timeStampInt , commodities[i].getItemID() , 0 , newPrice , 0, "n/a", "empty" , playerInstance);
			naturalFluctuationArray[i] = naturalReceipt;
		}

		return naturalFluctuationArray;
	}

	@Override
	public String toString(){
		for (TradingAccount x: players){
			System.out.println("Player in Lobby: " + x.getName() + " Lobby ID: " + lobbyID);
		}
		return "hello";
	}



	public PurchaseOrSaleReceipt newsEventStart(){

		Random rand = new Random();

		News newsItem = newsList.get(rand.nextInt(newsList.size()));  //rand.nextInt(newsList.size())

		int commodityAffected = newsItem.getCommodityImpacted();


		if(newsItem.isRumour()){

			commodities[commodityAffected].setPositiveProbability( newsItem.getPositiveProbability() );
			commodities[commodityAffected].setPositiveImpactMultipler( newsItem.getPositiveImpactMultipler() );
			commodities[commodityAffected].setNegativeImpactMultipler( newsItem.getNegativeImpactMultipler() );
			commodities[commodityAffected].setRumourTimeRemaining( newsItem.getRumourTime() );
		}

		if(!newsItem.isRumour()){
			commodities[commodityAffected].setCurrentPrice( commodities[commodityAffected].getCurrentPrice() * newsItem.getInstantPriceChangeMultiplier() );


		}

		int timeStampInt = toIntExact(gameTimer);
		PurchaseOrSaleReceipt newsReceipt = new PurchaseOrSaleReceipt("Server" , timeStampInt , commodityAffected , 0 , 0 , 0, "rumour" , newsItem.getName(), null);


		System.out.println("Rumour :" + newsItem.getName());
		newsList.remove(newsReceipt);
		return newsReceipt;
	}


	public String generatePlayersAndEquityString(){
		String x = "";

		DecimalFormat numbrFormat = new DecimalFormat("#.00");
		for(TradingAccount y: players){

			String fundsAvaliable = String.valueOf( numbrFormat.format(y.getEquity()));
			x += y.getName() + " " + fundsAvaliable + "\n";
		}

		return x;
	}

}
