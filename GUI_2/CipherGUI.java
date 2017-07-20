import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;
	private String keyword, filename, newfilename;

	//application instance variables
	//including the 'core' part of the textfile filename
	//cipher and letter frequency generating objects
	private MonoCipher mcipher;
	private VCipher vcipher;
	private LetterFrequencies letterfrequency;
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
		
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		keyField.addActionListener(this);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10); 
		messageField.addActionListener(this);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==monoButton)
		{	
			processFile(false) ;
			frequencyreport() ;		
		}
		else if (e.getSource()== vigenereButton)
		{
			processFile(true)  ;	
			frequencyreport() ;
		}
	   
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{	
		keyword = keyField.getText() ;
		if (!keyword.equals("") ) //keyword not empty
		{
			for (int i = 0 ; i < keyword.length(); i++) //iterate over keyword
			{
				for (int j = 0 ; j < keyword.length(); j++){
				
					char c = keyword.charAt(i) ; //read each character by charater
					
					if (!(c>='A'&&c<='Z')) //checking that keyword is in capital letters
					{
						JOptionPane.showMessageDialog(null, "Please enter "
								+ "only capital letters for keyword");
						return false ;
					}
					if (c==keyword.charAt(j) && i!=j) //check repeated letters
					{
						JOptionPane.showMessageDialog(null, "Please enter "
								+ "distinct letters for keyword");
						return false ;
					}
				}
			}
		}
		else if (keyword.equals("")) //keyword not entered
		{
			JOptionPane.showMessageDialog(null, "Please enter keyword");
				return false ;
		}
		return true;  
	}
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		if (getKeyword()==false) //start only if keyword is correct
		{
			return false ;
		} 
		filename = messageField.getText() ;//get file name fom GUI
	
		//check whether file name ends in P, C or not
		if (filename.endsWith("P")||filename.endsWith("C"))
		{
			return true ;
		}
		else if (!filename.endsWith("P")||!filename.endsWith("C"))
		{
			JOptionPane.showMessageDialog(null, "Please enter a valid file name");
			return false ;
		}
		else if (filename.equals("")) //check if file name is not entered
		{
			JOptionPane.showMessageDialog(null, "Please enter file name");
			return false ;
		}
		return true;  
	}	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{	
		if (processFileName()==false) //start only if filename is correct
		{
			return false ;
		} 
		
		if (vigenere == false)
		{
		mcipher = new MonoCipher(keyword) ;//parse keyword to monocipher
		}
		else if (vigenere==true)
		{
		vcipher = new VCipher(keyword) ;//parse keyword to vcipher
		}
		//create object to generate letter frequency analysis report
		letterfrequency = new LetterFrequencies() ;
		
		FileReader reader = null; //reader to read supplied file
        FileWriter writerC = null, writerD = null; //writers of encrypts and decrypts
        
        newfilename = filename.substring(0,(filename.length()-1)) ;
        
        try {
        	//file names 
        	reader = new FileReader(filename+".txt"); //reader of supplied file
        	writerC = new FileWriter(filename+"C.txt"); //writer of encrypted data
        	writerD = new FileWriter(filename+"D.txt"); //writer of decypted data
        	 
        	/*
        	 * loop to read supplied file character by character
        	 * decode/encode it by monocipher or vcipher 
        	 * and write decoded/encoded data to respective files.
        	 * Also parses character to letter frequency report updates
        	 */
            for(;;) 
            {
            	int c = reader.read(); char cp=' ' ;
            	
            	 if (c==-1){break ;}
            
                char ch = (char) c ; //read character
             
                if(filename.endsWith("P")) //plain text
                {
                	if (!vigenere) //not vigenere
                	{ 
                		cp = mcipher.encode(ch) ; //call monocipher
                	}
                	else if (vigenere) //if vigenere
                	{
                		cp = vcipher.encode(ch) ; //call vcipher
                	} 
                	letterfrequency.addChar(cp); //increment frequency of character 
                	writerC.write(cp); //write to encrypted file
                }
                else if(filename.endsWith("C"))// cipher text
                {
                	if (!vigenere) //not vigenere
                	{
                		cp = mcipher.decode(ch) ; //call monocipher
                	}
                	else if (vigenere) //if vigenere
                	{
                		cp = vcipher.decode(ch) ; //call vcipher
                	}
                	letterfrequency.addChar(cp); //increment frequency
                	writerD.write(cp); //write to decrypted file
                }
            }
            //close file reader and writers
            reader.close();
            writerC.close();
            writerD.close();
           
        } 
        catch(IOException e) //incase its not able to open or write files
        {
            JOptionPane.showMessageDialog(null, "File not found");
        }
        
		return true ; 
	}
	/*
	 * method to generate frequency report for each character
	 */
	public void frequencyreport()
	{		
		PrintWriter writer ; //writer of frequency report to file
		try 
		{	
			writer = new PrintWriter(filename+"F.txt") ;
			//print column names of frequency report
			writer.println(""+"Letter"+" "+"Freq" +"   "+"Freq%"+"  "+"AvgFreq%"+"   "+"Diff");
			for (int j = 0; j < 26; j++) //iterate over alpahabet size
			{	
				//get report for given character with its corresponding index in alpahabet
				writer.println(letterfrequency.getReport((char)j,j)); 
			}
			double maxFreq = letterfrequency.getmaxFreq() ;
			writer.println(String.format("The most frequent character is %s "
						+ "at %.1f %s ", letterfrequency.getMaxPC(), maxFreq,"%")); 
			writer.close(); //close writer
		}
		catch (IOException x) //incase it is not possible to write this file
		{
			x.printStackTrace();
		}

	} 
}
