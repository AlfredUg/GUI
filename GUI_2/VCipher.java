/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode 
 * and decode a character
 */
public class VCipher
{	
	/*
	 * instant variables 
	 */
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	private char [][] cipher ; //cipher array
	private String key ; //keyword
	private int totChars ; //total number of chracters decoded/encoded
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		key = keyword ; //instantiate keyword
		
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) 
		{
			alphabet[i] = (char)('A' + i);
		}
		
		//create cipher array		
		cipher = new char[key.length()][SIZE];
		
		//insert keyword in first column of cipher array
		for(int i = 0; i < key.length(); i++)
		{
			cipher[i][0] = key.charAt(i) ;
		}
//populate the rest of cipher array with remaining alphabet characters
		for(int i = 0; i < key.length(); i++){ //iterate over rows
			for(int j = 1; j < SIZE; j++){ //start with second column
				char c = cipher[i][j-1] ; //read character in previous cell
				
				if (c =='Z') //check end of alphabet letters
				{
					c = 'A'-1 ; //set to character before A in alphabet
				}
				cipher[i][j] = (char) (c + 1); //set next alphabet character to this cell
			}
		}
		//printing out cipher array for confirmation
		for(int i = 0; i < key.length(); i++){
			for(int j = 0; j < cipher[i].length; j++){
				System.out.print(cipher[i][j]);
			}
			System.out.println();
		}
	}
	/*
	 * Encode a character
	 * @ch the character to be decoded. 
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{	
		char enc = 0 ; //initialize encoded character
		//row of cipher array to enccode character
		int k = totChars % key.length() ;
		for (int j=0; j<alphabet.length;j++) //iterate over columns	
		{				
			if (ch==alphabet[j]) //check for character in alphabet
			{	
				totChars++ ;//increment number of characters
				
				enc = cipher[k][j] ; //get character at corresponding position in cipher array
			}	
			else if (!(ch>='A'&&ch<='Z')) //for non capital characters
			{
				enc = ch ;
			}
		}
		System.out.println(totChars);
	    return enc; //encoded character
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded 
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
		//row of cipher array to deccode character
		int k = totChars % key.length() ;
		char dec = 0 ; //initialize decoded character
		for (int j=0; j<alphabet.length;j++)//iterate over columns	
			if (ch == cipher[k][j]) //check for character in cipher array
			{	
				totChars++ ;//increment number of characters
				
				dec =   alphabet[j] ; //get character at corresponding position in alphabet
			}
			else if (!(ch>='A'&&ch<='Z')) //non capital alphabet characters
			{
				dec = ch ;
			}
	    return dec; //decoded character
	}

}
