package assingment1;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

//Vinay Kumar - C17474442	
public class FlightreservationFrame extends JFrame {

	
	private JPanel panel = new JPanel();
	private String [] cities = {"Dublin", "Copenhagen", "Oslo" , "San Francisco", "Edinburgh" , "New York"};
	//Panel 1
	private JPanel p1 = new JPanel();
	private JLabel departure = new JLabel("Departure:");
	private JLabel destination = new JLabel("Destination:");
	private JButton search = new JButton("Search");
	private JButton show = new JButton("Show All");
	private JComboBox dep;
	private JComboBox dest;
	//panel 2 (Table)
	private JPanel p2 = new JPanel();
	
	//panel 3 - tickets, purchase, date
	private JPanel p3 = new JPanel();
	private JLabel tickets = new JLabel("Number of Tickets");
	private JButton purchase = new JButton("Search");
	//date 
	
	public FlightreservationFrame(){
		
		//reference the abstract model class for the table and create it
		FlightReservationModel tableModel = new FlightReservationModel();
		JTable tbl = new JTable(tableModel);
			
		//set Combo boxes
		dep = new JComboBox(cities);
		dest = new JComboBox(cities);
		
		//Create scroll pane and pass the table into it 
		JScrollPane srcoll = new JScrollPane(tbl);
				
		//add to panel 1
		p1.add(departure);
		p1.add(dep);
		p1.add(destination);
		p1.add(dest);
		p1.add(search);
		p1.add(show);
		//add to panel 2
		
		//add to panel 3 
		
		//add to main panel 
		add(panel);
		panel.add(p1);
		panel.add(p2);
		
		
		//set frame properties
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setTitle("Flight Reservation");
		
		
	
		
	}//end 
	
	
	public static void main(String[] args) {
		new FlightreservationFrame();

	}//End main 

}//End class 
