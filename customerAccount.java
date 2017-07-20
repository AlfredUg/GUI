/*
 * customerAccount class to store account name and update customers' balance
 */
public class customerAccount {
	//instant variables
	private  Double currentBalance = 0. ;
	private String accountName = "";
	
	//constructor
	//Initializing instant variables
	public customerAccount(Double balance, String name){
		currentBalance = balance ;
		accountName = name ;
	}
	
	//Helper methods
	//processing sales
	public  double sales(int numBottles, double  costBottle){
		double  totalcost =  numBottles * costBottle ;	
		currentBalance = currentBalance + totalcost ;
		return  totalcost ;
	}
	//processing returns
	public  double returns(int numBottles, double costBottle){
		double charge =0.2;
		double totalcost =  numBottles * costBottle*(1-charge);
		currentBalance = currentBalance - totalcost ;
		return  totalcost ;
	}
	
	//Accessor methods
	//method to obtain name of account
	public  String getName()
	{
		return accountName ;
	}
	//method to obtain current balance
	public Double getBalance()
	{
		return currentBalance ;
	}
		
}
