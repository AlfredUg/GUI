import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LWMGUI extends JFrame implements ActionListener {
	//instant variables
	private JButton processSale , processReturn ;
	private JTextField winenameField, quantityField, priceField, 
	transAmountField, currentBalField;
	private JLabel winepurchased_lab ;
	
	/*instantiating customerAccount class to be used in updating
	  GUI with balance and transaction amount
	*/
	private customerAccount account  ;
		
	//constructor for LMGUI
	public LWMGUI(customerAccount Account){
		
		//initialising customerAccount class
		account =  Account ;
		
		//Title of GUI window
		setTitle(String.format("Lilybank Wine Merchants: %s", 
								account.getName())) ;
		
		//Size of GUI window
		setSize(500,200) ;
		
		//location of GUI window on monitor screen
		setLocation(400,200) ;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//Initializing buttons 
		processSale = new JButton("Process Sale") ;
		processSale.addActionListener(this);
		processReturn = new JButton("process Return") ;
		processReturn.addActionListener(this);
		
		//creating labels of textfields of the GUI
		JLabel winename_lab = new JLabel("Name: ");
		JLabel quantity_lab = new JLabel("Quantity: ");
		JLabel price_lab = new JLabel("Price: £");
		JLabel amount_lab = new JLabel("Amount of transaction: ");
		JLabel balance_lab = new JLabel("Current Balance: ");
		winepurchased_lab = new JLabel("Wine purchased: " );
		
		//creating text fields , adding listeners and setting field width
		winenameField = new JTextField(15) ; 
		winenameField.addActionListener(this);
		quantityField = new JTextField(3) ; 
		quantityField.addActionListener(this);
		priceField = new JTextField(4) ; 
		priceField.addActionListener(this);
		transAmountField = new JTextField(6) ; 
		transAmountField.addActionListener(this); 
		transAmountField.setEditable(false);
		currentBalField = new JTextField(6) ; 
		currentBalField.addActionListener(this);
		currentBalField.setEditable(false);
		
		//setting Layout of panels on the GUI window frame
		GridLayout grid = new GridLayout(4,0) ;
		setLayout(grid) ;
		
		//creating panels for the GUI 
		//first panel
		JPanel pan1 = new JPanel() ;
		pan1.add(winename_lab) ; pan1.add(winenameField) ; 
		pan1.add(quantity_lab) ; pan1.add(quantityField) ; 
		pan1.add(price_lab) ; pan1.add(priceField) ;
		add(pan1) ;
		//second panel
		JPanel pan2 = new JPanel() ;
		pan2.add(processSale) ; pan2.add(processReturn); 
		add(pan2) ;
		//third panel
		JPanel pan3 = new JPanel(new GridLayout(0,2)) ;
		pan3.add(winepurchased_lab) ; 
		add(pan3) ;
		//fourth panel
		JPanel pan4 = new JPanel() ;
		pan4.add(amount_lab) ; pan4.add(transAmountField) ; 
		pan4.add(balance_lab) ; pan4.add(currentBalField) ;
		add(pan4) ; 
 		
		//Enabling GUI to be displayed
		setVisible(true) ;	
		
		//Displaying account balance depending on whether it is credit or not
		if (account.getBalance() >= 0)
		{
			currentBalField.setText(String.format("£"+"%6.2f", 
									account.getBalance())) ;
		}
		else
			 currentBalField.setText(String.format("£"+"%6.2f"+"CR",
					 				account.getBalance()*-1));
	}
	
	//method to get details from the textfields , create and return Wine object 
	private Wine textDetails()
			{
		//initialising winename, quantity and price
			String wineName = null ; 
			int wineQuantity = 0 ; 
			double winePrice = 0. ; 
						
			wineName = winenameField.getText().trim();
			//checking if the name of wine is not empty	
			
			if (wineName.length()==0)
			{				
				JOptionPane.showMessageDialog(null, "please enter name "
						+ "of wine", "Message", JOptionPane.ERROR_MESSAGE);
			}
			
			//checking if number of bottles is positive integer
			try
			{					
				wineQuantity = Integer.parseInt(quantityField.getText());
				if (wineQuantity <= 0) //if a negative number
				{	
					JOptionPane.showMessageDialog(null, "Quantity should be "
							+ "a positive integer","Input error message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (NumberFormatException e)
			{	//inform user of invalidity of entered data
				JOptionPane.showMessageDialog(null, "Quantity should be "
						+ "a positive integer","Input error message", JOptionPane.ERROR_MESSAGE);
			}
			
			//checking if bottle price is a positive double 
			try
			{
				winePrice = Double.parseDouble(priceField.getText()) ;
				if (winePrice <= 0) //if negative inputs 
				{	
					JOptionPane.showMessageDialog(null, "price should be a number",
									"Input error message",	JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "price should be a positive number", 
												"Input error message", JOptionPane.ERROR_MESSAGE);
			}			
			//creating Wine object with arguments from the textfields	
		
			Wine wine =  new Wine(wineName,winePrice,wineQuantity) ; 
			return wine ;
		}
	
	// Event handler method
	public void actionPerformed(ActionEvent e) 
		{
		//obtaining data entered 
		String name = textDetails().getname();
		int quant = textDetails().getquantity() ;
		double price = textDetails().getprice() ;
		
		//processing and displaying sales rounded to two decimal places
		if (e.getSource()==processSale){
		transAmountField.setText(String.format("£"+"%6.2f" , account.sales(quant,price)));
		priceField.setText(""); quantityField.setText("") ;
		}
		//processing and displaying returns rounded to two decimal places
		else if (e.getSource()==processReturn)
		{
		transAmountField.setText(String.format("£"+"%6.2f", account.returns(quant, price)));
			priceField.setText(""); quantityField.setText(""); 
		}
	
		//Checking sign of current balance and displaying it rounded to two decimal places.
		
		if (account.getBalance() >= 0)
		{
			currentBalField.setText(String.format("£"+"%6.2f", account.getBalance())) ;
		}
		else
			 currentBalField.setText(String.format("£"+"%6.2f"+"CR", account.getBalance()*-1));
		//confirming purchased wine
		winepurchased_lab.setText("Wine purchased: "+ name);
	}
}