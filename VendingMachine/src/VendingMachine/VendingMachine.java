package VendingMachine;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class VendingMachine implements Runnable {
	
	private enum Coin {
	    PENNY(1.0), NICKEL(5.0), DIME(10.0), QUARTER(25.0), DOLLAR(100.0);
	    Coin(double value) { this.value = value; }
	    private final double value;
	    public double value() { return value; }
	}
	
	private enum SelectionMenu {
		COLA,
		ORANGE_JUICE,
		COFFEE,
		QUIT
	}
	
	private static final String ENGAGING = "Still engaging"; 
	private static final String EXIT = "Exit"; 
	private DrinkChamber drinkChamber;
	private double amountPaid; 
	private Socket clientSocket = null;
	private String serverName = null;
	private InputStream input = null;
	private OutputStream output = null;
	
	public VendingMachine(Socket clientSocket, String serverName) {
		this.clientSocket = clientSocket;
		this.serverName = serverName;
		this.powerUpVendingMechine();
	}
	
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	private void powerUpVendingMechine() {
		drinkChamber = new DrinkChamber();
		drinkChamber.loadInventory();
	}
	
	private void displayMenu() {
		try {
			output.write(("Please select your drink from the menu:\n").getBytes());
			output.write(("\t" + SelectionMenu.COLA + "\t\tprice: [" + drinkChamber.cola.price + "]cents" + "\tstill have: [" + drinkChamber.getColaCount().toString() + "]can\n" +
							"\t" + SelectionMenu.COFFEE + "\t\tprice: [" + drinkChamber.coffee.price + "]cents" + "\tstill have: [" + drinkChamber.getCoffeeCount().toString() + "]can\n" +
							"\t" + SelectionMenu.ORANGE_JUICE + "\tprice: [" + drinkChamber.oj.price + "]cents" + "\tstill have: [" +  drinkChamber.getOJCount().toString()+ "]can\n" +
							"\t" + "QUIT\n\n" +
							"Enter:").getBytes());
		} catch (IOException e) {};
	}
	
	public void run() {
		do {
			try {
				input = this.clientSocket.getInputStream();
				output = this.clientSocket.getOutputStream();
				output.write(("HTTP 200 - server:" + this.serverName + " started at " + System.currentTimeMillis()).getBytes());
				setAmountPaid(0.0);
				displayMenu();
				output.flush();
			} catch (IOException e) {
				System.out.println("Error in socket io.");
			}
		} while (!captureInputAndRepond().equals("Exit"));	
	}
	
	private double calculateChange(double price, String insertedCoins) {
		StringTokenizer st = new StringTokenizer(insertedCoins);
		while(st.hasMoreElements()) {
			String coin = st.nextToken();
			if (coin.equals("PENNY")) { amountPaid += Coin.PENNY.value; }
			else if (coin.equals("NICKEL")) { amountPaid += Coin.NICKEL.value; }
			else if (coin.equals("DIME")) { amountPaid += Coin.DIME.value; }
			else if (coin.equals("QUARTER")) { amountPaid += Coin.QUARTER.value; }
			else if (coin.equals("DOLLAR")) { amountPaid += Coin.DOLLAR.value; }
		}
		try {
			output.write(("You have paid " + amountPaid + " cents").getBytes());
		} catch (IOException e) {};
		return amountPaid - price;
	}
	
	private void processSelection(String selection, boolean paymentOK) throws Exception {
		double price = 0.0;
		switch (SelectionMenu.valueOf(selection)) {
			case COLA: 
				price = drinkChamber.cola.price;
				if (paymentOK) { drinkChamber.takeACola(); } 
				else {
					output.write(("The price is " + drinkChamber.cola.price + " cents, please put in the coin.").getBytes());
					captureMoney(selection, price);
				}
				break;
			case COFFEE: 
				price = drinkChamber.coffee.price;
				if (paymentOK) { drinkChamber.takeACoffee(); } 
				else {
					output.write(("The price is " + drinkChamber.coffee.price + " cents, please put in the coin.").getBytes());
					captureMoney(selection, price);
				}
				break;
			case ORANGE_JUICE: 
				price = drinkChamber.oj.price;
				if (paymentOK) { drinkChamber.takeAOJ(); } 
				else {
					output.write(("The price is " + drinkChamber.oj.price + " cents, please put in the coin").getBytes());				
					captureMoney(selection, price);
				}
				break;
			case QUIT:
				throw new UserExitSelection();
		}
	}
	
	private void displayReturningCoins(double change) {
		try {
			output.write(("Returning coin: " ).getBytes());
			if ( change / Coin.DOLLAR.value >= 1 ) { 
				int numDollar = (int)change / (int)Coin.DOLLAR.value;
				change = change - (numDollar * Coin.DOLLAR.value);
				output.write((numDollar + " dollar ").getBytes());
			}
			if ( change / Coin.QUARTER.value >= 1 ) { 
				int numQuarter = (int)change / (int)Coin.QUARTER.value;
				change = change - (numQuarter * Coin.QUARTER.value);
				output.write((numQuarter + " quarter ").getBytes());
			}
			if ( change / Coin.DIME.value >= 1 ) { 
				int numDime = (int)change / (int)Coin.DIME.value;
				change = change - (numDime * Coin.DIME.value);
				output.write((numDime + " dime ").getBytes());
			} 
			if ( change / Coin.NICKEL.value >= 1 ) { 	
				int numNickel = (int)change / (int)Coin.NICKEL.value;
				change = change - (numNickel * Coin.NICKEL.value);
				output.write((numNickel + " nickel ").getBytes());
			} 
			if ( change / Coin.PENNY.value >= 1 ) { 
				int numPenny = (int)change / (int)Coin.PENNY.value;
				change = change - (numPenny * Coin.PENNY.value);
				output.write((numPenny + " penny ").getBytes());	
			}
		} catch (IOException e) {};
	}
	
	private void captureMoney(String selection, double price) throws Exception {
		String amount = null;
		
		BufferedReader coins = new BufferedReader(new InputStreamReader(input));
		try {
			amount = coins.readLine();
			if (amount != null) {
				double change = calculateChange(price, amount);
				if ( change >= 0.0) {
					processSelection(selection, true);
					if (change > 0.0) {
						System.out.println("Your change is: " + change + " cents");
						displayReturningCoins(change);
						output.write(("Thank you for your business, see you again! Exiting....").getBytes());
					}
				} else {
					System.out.println("You did not put enough money, please put in more coin.");
					captureMoney(selection, price);
				}
			}
		} catch (IOException e) {
			System.out.println("Error in reading input.");
			System.exit(1);
		}	
	}
	
	/**
	 * @return
	 */
	private String captureInputAndRepond() {
		String selection = null;
		BufferedReader choosen = new BufferedReader(new InputStreamReader(input));
		try {
			selection = choosen.readLine();	
		} catch (IOException e) {
			System.out.println("Error in reading input.");
			System.exit(1);
		}
	
		if (selection != null) {
			try {
				if (!selection.equals(SelectionMenu.QUIT)) System.out.printf("You have selected " + selection + "\n");
				processSelection(selection, false);
			} catch (Exception e) {
				if (e instanceof java.lang.IllegalArgumentException) { 
					System.err.println("Please select the choice listed in the menu.");
					captureInputAndRepond();
				} else if (e instanceof OutOfStockException) {
					 System.err.println(e.toString());
					 System.err.println("We have returned your money just now.");
					 displayReturningCoins(amountPaid);
				} else { System.err.println(e.toString()); } 
				return EXIT;
			}
		}
		return ENGAGING;
	}
	
	class DrinkChamber {
		Container<Cola, Integer> colaContainer = new Container<Cola, Integer>();
		Container<Coffee, Integer> coffeeContainer = new Container<Coffee, Integer>();
		Container<OrangeJuice, Integer> ojContainer = new Container<OrangeJuice, Integer>();
		
		Cola cola = new Cola();
		Coffee coffee = new Coffee();
		OrangeJuice oj = new OrangeJuice();
		
		public void loadInventory() {
			colaContainer.addItem(cola, new Integer(10));
			coffeeContainer.addItem(coffee, new Integer(10));
			ojContainer.addItem(oj, new Integer(10));
		}
		
		public void takeACola() throws Exception {
			if (getColaCount().intValue() - 1 < 0)
				throw new OutOfStockException("cola");
			colaContainer.addItem(cola, getColaCount().intValue() - 1);
		}
		
		public void takeACoffee() throws Exception {
			if (getCoffeeCount().intValue() - 1 < 0)
				throw new OutOfStockException("coffee");
			coffeeContainer.addItem(coffee, getColaCount().intValue() - 1);
		}
		
		public void takeAOJ() throws Exception {
			if (getOJCount().intValue() - 1 < 0)
				throw new OutOfStockException("orange juice");
			ojContainer.addItem(oj, getColaCount().intValue() - 1);
		}
		
		public Integer getColaCount() {
			return colaContainer.getItemCount(cola);
		}
		
		public Integer getCoffeeCount() {
			return coffeeContainer.getItemCount(coffee);
		}
		
		public Integer getOJCount() {
			return ojContainer.getItemCount(oj);
		}
	}
}
