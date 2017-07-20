/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
				
		// create first part of cipher from keyword
		char [] keywordCipher = new char [keyword.length()] ;
		for (int j= 0; j < keyword.length() ; j++)
			keywordCipher[j] = keyword.charAt(j) ;
		/**
		 * creating remainder of cipher from the remaining characters of the alphabet
		 * I do this in two steps.
		 * 1. create reversed alphabet with null characters at positions 
		 * of keyword letters.
		 * 2. create a new array without null characters.
		 */
		
		//creating array to accomodate reversed alphabet
		char [] rcipher = new char [SIZE] ;
		 
		for (int i = 0; i < SIZE; i++) //iterating over size of alphabet
		{	
			rcipher[i] = (char)('Z'-i) ; //forming reversed alphabet
			
			for (int j = 0; j<keyword.length();j++)
			{
				if (rcipher[i]==keywordCipher[j]) //checking if a charcter is part of keyword
				{
					rcipher[i] = ' ' ; //assigning keyword characters a null character
				}
			}
			
		}
		
		/*
		 * 2. creating a new array  without null characters
		 */
		
		//counting not null characters
	    int n = 0;
	    for (int i = 0; i < rcipher.length; i++)
	        if (rcipher[i] != ' ')   	
	        	n++;
	    // allocating new array with a size of number of not null characters
	    char [] newcipher = new char [n] ;
	    
	    //copying the not null characters
	    int j = 0;
	    for (int i = 0; i < rcipher.length; i++)
	        if (rcipher[i] != ' ')
	            newcipher[j++] = rcipher[i];
		
		//Cipher array, combination of the first and second cipher arrays
		cipher = new char [keywordCipher.length+newcipher.length] ;
		
		/*
		 * populating the cypher array in two steps.
		 * 1. putting the array of keyword characters.
		 */
		int count = 0;
		for (int i = 0; i<keywordCipher.length; i++)
		{
			cipher[i] = keywordCipher[i] ;
			count ++ ; //increamenting the count over size of keyword
		}
		// 2. appending the last array of characters
		for (int k=0; k<newcipher.length; k++)
		{
			cipher[count++] = newcipher[k] ; 
		}
		// print cipher array for testing and tutors
		System.out.print(cipher);
	}
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		char enc = 0  ; //encoded character initiation
		
		for (int i = 0; i < SIZE ; i++) //iterate over alphabet
		{	
			if (ch==alphabet[i]) //check character in alphabet
			{
				enc = cipher[i] ; //get corresponding character in cipher array
			}
			else if (!(ch>='A'&&ch<='Z'))
			{
				enc = ch ;
			}
		}	
		return enc; //encoded character
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{	
	char dec = 0  ; //initialize decoded character
	for (int j = 0; j < SIZE ; j++)
	{
		if (ch==cipher[j]) //check character in cipher array
		{
			dec = alphabet[j] ; //get corresponding character in alphabet
		}
		else if (!(ch>='A'&&ch<='Z'))
		{
			dec = ch ;
		}
	}
    return dec;  //decoded character
	}
}
