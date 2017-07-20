import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram 
{	
	//instant variables
	int maxClass ;
	int [] times ;
	FitnessClass [] fitclassArray , notnullfitArray;
	
	//default constructor
	public FitnessProgram()
	{	
		maxClass = 7 ;
		//instantiate the array of fitness classes
    	fitclassArray = new FitnessClass[maxClass] ;
    	 
    	//instantiating and initialising array of time slots
    	times = new int [maxClass] ; 
    	for (int k = 0 ; k < times.length; k++)
    	{
    		times[k] = 9+k ;
   
    	}
	}
	
	/*
	 * method to build list of classes in order of starting times.
	 * @params: a string which is a line read from classIn file
	 */
	public void buildArray(String classIn)
    {    
		//create a fitness class 
    	FitnessClass fit = new FitnessClass(classIn) ;
    	
    	boolean found = false ;
    	/*
    	 * populate the class array in order their respective
    	 * starting times
    	 */
    	for (int j = 0 ; j < times.length ; j++)
		{
    		
			if (fit.getStartTime() == times[j])
			{	
				found = true ;
				fitclassArray[j] = fit ; 	
			}
			else 
				found = false ;	
		}
    	//build the not null array
    	buildNotNullArray() ;
    }
	/*
	 * method to build the not null array of classes
	 */
	public void buildNotNullArray()
	{
		//fo loop to count not null slots in the finess class array
		int n = 0;
	    for (int i = 0; i < fitclassArray.length; i++)
	    {
	        if (fitclassArray[i] != null)   	
	        	n++;
	    }
	    // allocating new array with a size of number of not null classes
	    notnullfitArray = new FitnessClass [n++] ;
	    
	  //copying the not null classes
	    int j = 0;
	    for (int i = 0; i < fitclassArray.length; i++)
	    {
	        if (fitclassArray[i] != null)
	        {
	        	notnullfitArray[j++] = fitclassArray[i];
	        }
	    }
	}
	 /*
	  * method to populate attendance list.
	  * @params: a line of text read from attendanceIn file
	  */
    public void attendance(String attendIn)
    {	    	
    	/*
    	 * splitting the text by single spaces 
    	 */
    	String [] attend = attendIn.split(" ") ;
    	//taking the first item which is the class id
    	String id = attend[0] ;
    	//searching the not null class array for this id and populating attendance
    	for (int i = 0 ; i < notnullfitArray.length; i++)
    	{
    		if (id.equals(notnullfitArray[i].getID()))
    		{
    			notnullfitArray[i].attendList(attend);
    		}
    	}		
    }
    
    //methods to return array of classes
    public FitnessClass[] getArray()
    {
    	return fitclassArray ;
    }
    //method to return array of time slots
    public int [] getTimeArray()
    {
    	return times ;
    }
     /*
     * method to return first start time available for a class
     * @param fitness class whose starting time is required
     */
    public int getStartTime(FitnessClass ft)
    {	
    	boolean check = false ;
    	//initializing start time 
    	int time = 0 ;
    	//iterating over array of classes
    	for (int i = 1  ; i <= fitclassArray.length ; i++)
    	{
    		//skipping empty slots and recording immediate previous time slot
    		if (fitclassArray[i]== null)
    		{
    			time =  times[i-1] ;
    			check = true ;
    		}
    		else 
    			check = false ;	
    	}
    	return time ;
    }  
    /*
     * method to delete class from list
     * @params class id
     */
    public void deleteClass(String ID)
    {	boolean found;
    	//iterate over array of classes
    	for (int i = 0 ; i < fitclassArray.length ; i++)
    	{	
    		//skip null slots
    		if(fitclassArray[i]==null)
	    	{
	    		found = false ;
	    	}
    		else if (ID.equals(fitclassArray[i].getID()))
    		{
    			//delete class of supplied id
    			fitclassArray[i] = null ;
    			break ;
    		}
    			
    	}
    }
    
    /*
     * method to insert class to a list
     * @params, class is, name, tutor's name and starting time
     */
    public void addClass(String id, String name, String tutor,String time)
    {	
    	/*
    	 * building a string of the arguments so as to be parsable 
    	 * to the fitness class
    	 */
    	String add = String.format("%s %s %s %s", id, name, tutor, time);
    	
    	//iterating over array of classes 
    	for (int i = 0 ; i < fitclassArray.length ; i++)
    	{	
    		//create a class at each iteration using data fro text fields
    		FitnessClass fit = new FitnessClass(add) ;
    		//check if this slot is available and add the created class 
    		if (fitclassArray[i]== null)
    		{
    			fitclassArray[i] = fit ;
    			break ;
    		}
    			
    	}
    }
    
    /*
     * method to return overall average
     */
    public double getOverallAverage()
    {
    	double sum = 0. ; //initializing sum of class averages
    	
    	//iterating over the not null finess array
    	for (int i = 0 ; i < notnullfitArray.length ; i++)
    	{
    		//incrementing the sum of averages 
    		sum += notnullfitArray[i].getAverage() ;
    	}
    	double avg = sum /notnullfitArray.length ;
    	return avg ;
    }
    
   /*
    * method to return sorted array of fitness classes 
    * in order of average attendances 
    */
    public FitnessClass [] getSortedList()
    {	
    	buildNotNullArray() ; //updating notnull fitnessclas array 
    	
    	//iterating over not null fitness class array
    	for (int i = 1 ;  i < notnullfitArray.length; i++)
    	{
    		//comparing two adjacent classes
    		notnullfitArray[i-1].compareTo(notnullfitArray[i]) ;
    	}
    	//sorting arrays in non increasing order of average attendances
    	Arrays.sort(notnullfitArray);
    
    	return notnullfitArray ;
    }
}
