package model;

import model.Interfaces.BankInterface;

public class Bank implements BankInterface {

	private static BankInterface bankInstance;
	private int money;
	private static final int MONEY_EVERY_LAP = 200;
	
	private Bank() {
		this.money = 1000000000; // bank has 1 bilion $ 
	}
	
	// this method is used to withdraw or deposit money to the bank
	public void transaction(int amount) {
		this.money += amount;
	}
	
	public int getStartMoney() {
		this.money -= MONEY_EVERY_LAP;
		return MONEY_EVERY_LAP;
	}
	
	// returns the only possible instance of the singleton class dices
	public static BankInterface getInstance() {
		if (bankInstance == null)
			bankInstance = new Bank();
		return bankInstance;
	}
}
