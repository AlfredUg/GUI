/**
 * Programming AE2
 * Processes report on letter frequencies
 */
	public class LetterFrequencies
	{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	//frequency of most frequent character
	private double maxFreq ;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		 
		 //total characters encoded/encrypted
		 totChars = 0 ;
		 
		 //count for each letter
		 alphaCounts = new int [SIZE] ;
		 
		 //Most frequency character
		 maxCh = ' ' ;
		
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{		 //counter for number of characters processed
	    	for (  int j = 0 ; j < alphabet.length ; j++)
	    	{
	            if(ch == alphabet[j]) {
	            //incrementing frequency of characters
	            alphaCounts[j] = alphaCounts[j] + 1; 
	            totChars++;}
	    	} 
	    	 //counting processed characters
	    	System.out.println(totChars);
	}
	
	/**
	 * Gets the most frequent character
	 * @return the most frequent character
	 */
	public char  getMaxPC()
     {
		int max  = alphaCounts[0]; //setting maximum as the first
		for ( int i = 0; i< alphaCounts.length; i++)
		{
			if (alphaCounts[i]>=max) //iterating over alphabet 
			{
				max = alphaCounts[i] ; //changing maximum frequency
				maxCh = alphabet[i] ; //changing most frequent character
			}
		}
	return maxCh ; //most frequent character.
	}
	/**
	 * @param character and corresponding index in alphabet
	 * Returns a String consisting of the full frequency report
	 * @return the frequency report of this character
	 */
	
	public String getReport(char c, int i)
	{	
		String s = "" ; //string to contain report
		 c = alphabet[i] ; //character to report
		int frequency = alphaCounts[i] ; //frequency of character
		double freq_percent = alphaCounts[i]*100/ totChars ; //percentage frequency 
		double avg_freq = avgCounts[i] ; //average frequency
		double diff = freq_percent - avg_freq ; 
		
		s = String.format("%s   %s     %d     %.1f     %.1f       %.1f", 
				"",c,frequency, freq_percent, avg_freq, diff) ;	
		System.out.println(s);//printing for confirmation
	  return s;  //frequency report
	}
	
	//method to get maximum frequency
	public double getmaxFreq()
	{
		maxFreq  = alphaCounts[0]; //setting maximum as the first
		for ( int i = 0; i< alphaCounts.length; i++)
		{
			if (alphaCounts[i]>maxFreq) //iterating over alphabet 
			{
				maxFreq = alphaCounts[i] ; //changing maximum frequency
			}
		}
		return maxFreq*100/totChars;
	}
}
