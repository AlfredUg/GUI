import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** Fitness programm class */
	public FitnessProgram fp ;
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() 
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		fp = new FitnessProgram() ;
		initLadiesDay() ;
		initAttendances() ;
		updateDisplay() ;
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() 
	{	
		FileReader reader ; //declare file reader
	    try
	    {
	    	//creating a new file reader to read from class in file
	    	reader =  new FileReader(classesInFile) ;
	    	 Scanner in  = new Scanner(reader) ;
	    	 while (in.hasNext())
	    	 {
	    		 String line = in.nextLine() ; //reading per line
	    		 fp.buildArray(line);  //including given class in class list
	    	 }
	    }
	    catch(IOException x)
	    {
	    	x.printStackTrace();
	    }
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances()
	{
		FileReader reader ; //declare file reader
	    try
	    {
	    	/*
	    	 * creating a new file reader object to read 
	    	 * from attendanceIn file
	    	 */
	    	reader =  new FileReader(attendancesFile) ;
	    	 Scanner in  = new Scanner(reader) ;
	    	 while (in.hasNext())
	    	 {
	    		 String line = in.nextLine() ; //reading per line  		
	    		 fp.attendance(line); //recording attendance 
	    	 }
	    }
	    catch(IOException x)
	    {
	    	x.printStackTrace();
	    }
	}
	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() 
	{
	   FitnessClass [] classesArray=  fp.getArray(); //getting array of fitness classes
	   
	   int [] time = fp.getTimeArray() ; //getting array of time slots
	   
	   /*
	    * initiaizing strings for displaying time slots with corresponding 
	    * class names and tutor names 
	    */
	   String classNnames = " ", tutorName = " ", timeSlot = " "; 
	   
	   int i ;
	   for (i = 0 ; i < classesArray.length ; i++)
	   {	
		   //formating a time slot display
		   timeSlot +=  String.format("%s"+"-"+"%s"+"%7s",
				   						time[i], time[i]+1," ") ;
		   
		   //for classes with allocated time slots 
		   if (classesArray[i]!=null)
		   {
			   classNnames += String.format("%s"+ "%4s", 
					   			classesArray[i].getClassName(), " ") ;
		 
			   tutorName += String.format("%s" + "%8s", 
					   classesArray[i].getTutorName(), " ") ;}
		   else if(classesArray[i]==null) //for time slots without classes allocated
		   {	
			  classNnames += String.format("%s"+ "%5s", "Available", " " );
			 
			  tutorName += String.format("%s"+ "%10s" , "  ", " ") ;
		   }
		   //adding  the current time table to text area
		   display.setText(timeSlot+"\n");
		   display.append(classNnames+"\n");
		   display.append(tutorName+"\n");
		   display.setVisible(true); //displaying the time table
	   }
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() 
	{
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		idIn.addActionListener(this);
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		classIn.addActionListener(this);
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		tutorIn.addActionListener(this);
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class to the array of classes
	 */
	public void processAdding() 
	{
		//getting text from text fields
	    String  id = idIn.getText() ;
	    String name = classIn.getText() ;
	    String tutor = tutorIn.getText() ;
	    
	    //initializing start time for the class to be added 
	    int  time = 0 ; 
	    boolean add = false ;
	    boolean notfull = false; //to keep truck of null slots
	    //loop to check if class list is full
	    for (int j = 0 ; j < fp.getArray().length ;j++ )
	    {
	    	if (fp.getArray()[j]==null)
	    	{
	    		notfull = true ;
	    	}
	    }
	    if (!notfull) //if the class list is full, give a warning
	    {
	    	JOptionPane.showMessageDialog(null, "Class List is full");
	    }
	    
	    //checking if class id is non empty
	    if(id.length()==0)
	    {
	    	JOptionPane.showMessageDialog(null, "please enter Class ID"
					, "Message", JOptionPane.ERROR_MESSAGE);
	    	
	    }
	    else if(name.length()==0) //checking if class name is non empty
	    {
	    	JOptionPane.showMessageDialog(null, "please enter Class name"
					, "Message", JOptionPane.ERROR_MESSAGE);
	    	
	    }
	    else if (tutor.length()==0) //checking if tutor's name is non empty
	    {
	    	JOptionPane.showMessageDialog(null, "please enter tutor's name ",
					"Input Error Message", JOptionPane.ERROR_MESSAGE);
	    }
	    else if (name.length()!=0 && tutor.length()!=0 )
	    {	
	    	//loop to check for existing class with same id 
	    	for( int k =0; k < fp.getArray().length ; k++)
		    {
	    		//skip null positions
	    		if(fp.getArray()[k]==null)
		    	{
		    		add = false ;
		    	}
	    		else if(id.equals(fp.getArray()[k].getID()))
		    	{	//warning for non unique class id
		    		JOptionPane.showMessageDialog(null, "Class already "
		    		+ "exists", "Input Error Message", JOptionPane.ERROR_MESSAGE);
		    		add = false  ; 
		    	}
		    }
	    	//loop to find earliest available time possible
	    	for( int i =0; i < fp.getArray().length ; i++)
	    	{
	    		if (fp.getArray()[i]==null)
		    	{
	    			//add = true ;
		    		time = fp.getTimeArray()[i] ;
		
		    		//adding a class to th array of fitness classes
		    		fp.addClass(id, name, tutor, time+"");
		    		String s = String.format("%s %d %d %d %d %d", id,0,0,0,0,0) ;
		 		    fp.attendance(s);
		    		break ;
		    	}
	    	}			
	    }
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() 
	{
		boolean found; //boolean to truck class to be deleted 
		//getting class id from text field
		String  ID = idIn.getText().trim() ;
		
		if(ID.length()==0) //checking if id is not supplied 
		{	
			JOptionPane.showMessageDialog(null, "please enter Class ID"
						, "Message", JOptionPane.ERROR_MESSAGE);
		}
		else if(ID.length()!=0) //if id is supplied
		{			
			//loop to search for a class corresponding to supplied id
			for(int i = 0 ; i < fp.getArray().length ; i++)
		    {	
				//skipping empty slots 
				if(fp.getArray()[i]==null)
		    	{
		    		found = false ;
		    	}
				else if(ID.equals(fp.getArray()[i].getID()))
		    	{	
		    		found = true ;
		    		fp.deleteClass(ID); //deleting class
		    	}
				else 
					found = false ;
		    }			
		}		
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport()
	{
	  report = new ReportFrame(fp) ; 
	  report.setVisible(true);
	}
	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() 
	{
	    PrintWriter writer ; //defining a writer
	    boolean write  ; //variable to track writing of text to out file
	    try
	    {	
	    	writer = new PrintWriter(classesOutFile) ;
	    	//iterating over array of fitness classes
	    	for(int i = 0 ; i < fp.getArray().length ; i++)
	    	{	
	    		FitnessClass fit = fp.getArray()[i] ; //get array of classes
	    		
	    		if(fit==null) //skip null slots
	    		{
	    			write = false ;
	    		}
	    		else if (fit!=null)
	    		{
	    		write = true ;
	    		String classRecord = String.format("%3s %8s %6s %d",fit.getID(),
	    											fit.getClassName(), fit.getTutorName(),
	    											fit.getStartTime() ) ;
	    		writer.println(classRecord); //writing to class out file
	    		}
	    	}
	    	writer.close(); //closing writer 
	    	System.exit(0); //terminating program
	    }
	    catch(IOException x)
	    {
	    	JOptionPane.showMessageDialog(null, "Check it "
    	    		, "Input Error Message", JOptionPane.ERROR_MESSAGE);
	    }
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	    
		if(ae.getSource()==closeButton)
		{
			processSaveAndClose() ; //saving changes and teminating program
		}
		else if (ae.getSource() == attendanceButton)
		{ 
			displayReport() ; //displaying attendance report
			
		}
		else if (ae.getSource()==addButton)
		{
			processAdding(); //adding a class
			updateDisplay() ; //updating time table
		}
		else if (ae.getSource()==deleteButton)
		{
			processDeletion(); //deleting 
			updateDisplay() ; //updating time table
		}
	}
}
