
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/*
Main method for the lily bank wine merchants
*/
public class AssEx1{

	public static void main(String[] args) {
		//Initializing account balance
		Double  balance = 0.;
		
		//obtaining account name from user
		String name = JOptionPane.showInputDialog(null,"Please enter account name") ;
		
		//checking if name is non empty or not and response when user closes the dialog pane
		
			if (name == null || name.length()==0) 
			{
				System.exit(0) ;
			}
			
		//Checking that valid balance is entered by user
		boolean rightbalance = false ;
		while (!rightbalance){
		try {
			balance = Double.parseDouble(JOptionPane.showInputDialog(null,"Please enter current balance ")) ;
			rightbalance = true ;
			}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Account balance should a number", "Input Error Message", JOptionPane.ERROR_MESSAGE);
			}
		}
		//creating customerAccount object 
		customerAccount custAccount = new customerAccount(balance, name) ;
		//parsing customerAccount to a newly created LWMGUI object
		new LWMGUI(custAccount) ;
	
	}

}

