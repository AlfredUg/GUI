/*
 * Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    	
	//instant variables
	String classID, className, tutorName; //class id, name and tutor
	int startTime; //starting time of a class
	int  constant ; //class constant: number of weeks to record attendance

	int [] records ; //array of attendance records
	
	/*
	 * non default constructor to initialize instant variables
	 * @param a line of text read from classIn file
	 */
	public FitnessClass(String classIn)
	{	
		/*
		 * splitting the line of text by single space 
		 * to obtain values to initiate instant variables
		 */
		String [] classes = classIn.split(" ") ; 
		classID = classes[0] ; 
		className = classes[1] ; 
		tutorName = classes[2] ; 
		startTime = Integer.parseInt(classes[3]); 
		constant = 5 ; 
		//instantiating array of attendance records
		records = new int [constant];
		 
	}
	/*
	 * method to populate attendance records.
	 * @param a string array consisting attendance records
	 * from attendanceIn file
	 */
	public void attendList(String [] attendIn)
	{	
		//iterate but start from second so as to neglect class id
		for (int i = 1 ; i < attendIn.length; i++)
		{
			records[i-1] = Integer.parseInt(attendIn[i]) ;
		}
	}
	//accesor method to return class id 
	public String getID()
	{
		return classID ;
	}
	//accesor method to return class name 
	public String getClassName()
	{
		return className ;
	}
	//accesor method to return tutor's name 
	public String getTutorName()
	{
		return tutorName ;
	}
	//accesor method to return class start time  
	public int getStartTime()
	{
		return startTime ;
	}
	/*
	 * method to return average attendance of the class
	 */
	public double getAverage()
	{	
		//initializing sum of attendance records
		double sum = 0. ; 
		//iterating over attendance records
		for (int j = 0 ; j < records.length; j++)
		{	
			int rec = records[j] ;
			sum += rec ; //incrementing the sum
		}
		double avg = sum/records.length ; //calculating average
		return avg ; 
	}
	/*
	 * method to return detailed report of a class
	 */
	public String getReport()
	{	
		//initializing string of attendance records
		String attendance = "" ;
		//iterating over the records array 
		for (int i = 0 ; i < records.length ; i++)
		{	
			//concatenating the records with a single spacing
			attendance += String.format("%s ",records[i]);
		}
		//formating the class report as required.
		String report = String.format("%4s %10s %10s %25s"+"%10s"
		+" %.2f", classID, className, tutorName,  attendance," " ,getAverage());
		return report ;
	}
	/*
	 * method to compare two classes by there average attendance
	 * @param: Another fitness class 
	 */
    public int compareTo(FitnessClass other) 
    {
    	//calculating difference between the compared classes
    	double temp  = other.getAverage() - this.getAverage() ;
    	return (int) temp; 
    }
}