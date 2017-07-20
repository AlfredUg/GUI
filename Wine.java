/*
 * Wine class to store name, quantity and prce of wines
 */
public class Wine {
	
	//instant variables: name of wine, price of a bottle and number of bottles
	private String wineName = "" ;
	private double bottlePrice = 0. ;
	private int quantityWine = 0 ;

	//constructor
	public Wine(String name, double price, int quantity){
		//initiating instant variables
		wineName = name ;
		bottlePrice = price ;
		quantityWine = quantity;
	}
	
	//Accessor methods 
	//method to obtain name of wine
	public String getname(){
		
		return wineName ;
	}
	//method to obtain price of a wine bottle 
	public double getprice(){
		
		return bottlePrice ;
	}
	//method to obtain number of wine bottles
	public int getquantity(){
		
		return quantityWine ;
	}
	
}
