import java.awt.*;
import javax.swing.*;

/*
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
       
	//instant variables 
	public FitnessProgram prog ;
	private JTextArea textArea  ;
		
	/*
	 * constructor to initialize instant variables
	 */
	public ReportFrame(FitnessProgram pro)
	{			
		prog = pro ;
		this.setTitle("Class Attendance Report");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(670,230);
		this.setLocation(500, 300);
	
		//initiating text area 
		textArea = new JTextArea();
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));
		this.add(textArea, BorderLayout.CENTER);
		textArea.setEditable(false);
		//adding text to text area
		buildText() ;
	}
	/*
	 * method to build text for the attendance report
	 */
	public void buildText()
	{	
		//getting a sorted array of classes
		FitnessClass [] report = prog.getSortedList() ;
		//formating the header of the report
		textArea.setText(String.format("%3s %10s %12s %20s"+"%10s"+"%s",
				"ID", "Name", "Tutor", "Attendance", " ",
				"Average Attendance"+"\n"));
		//setting a line of stars just below the header
		for(int i = 0 ; i < 80 ; i++)
		{
			textArea.append(String.format("*"));
		}
		//writing to a newline
		textArea.append("\n");
		//adding individual class report to the text area
		for (int i = 0 ; i < report.length ; i++)
		{
			String s = report[i].getReport() ;
			textArea.append(s+"\n");
		}
		//adding the overall average to the text area
		textArea.append(String.format("%30"
				+ "s"+"Overall Average attendance:"
				+ " "+"%.2f"," ", prog.getOverallAverage()));
	}
}
