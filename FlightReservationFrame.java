package assignment1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

//Vinay Kumar - C17474442	
public class FlightReservationFrame extends JFrame {

	
	private JPanel panel = new JPanel();
	private String [] cities1 = {"Dublin", "Copenhagen", "Oslo" , "San Francisco", "Edinburgh" , "New York"};
	private String [] cities2 = {"Dublin", "Copenhagen", "Oslo" , "San Francisco", "Edinburgh" , "New York"};
	
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
	JTextField numTick = new JTextField(10);
	private JButton purchase = new JButton("Purchase");
	//private JLabel date = new JLabel("Todays date : " + d); 
	//date 
	Date d = new Date();
	//private JLabel date = new JLabel("Todays date : " + strDate);
	//format date
	SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
	String strDate = dateF.format(d);
	private JLabel date = new JLabel("Todays date : " + strDate);
	
	public FlightReservationFrame(){
		
		//reference the abstract model class for the table and create it
		FlightReservationModel tableModel = new FlightReservationModel();
		JTable tbl = new JTable(tableModel);
			
		//set Combo boxes
		dep = new JComboBox(cities1);
		dep.setSelectedItem(cities1[0]);
		dest = new JComboBox(cities2);
		dest.setSelectedItem(cities2[1]);
		
		//Create scroll pane and pass the table into it 
		JScrollPane srcoll = new JScrollPane(tbl);
		
		//Create sorter and set sorter to the table
		TableRowSorter<TableModel> sorter = new TableRowSorter(tbl.getModel());
		tbl.setRowSorter(sorter);
				
		//add to panel 1
		p1.add(departure);
		p1.add(dep);
		p1.add(destination);
		p1.add(dest);
		p1.add(search);
		p1.add(show);
		//add to panel 2
		p2.add(srcoll);
		tbl.setFillsViewportHeight(true);
		tbl.setPreferredScrollableViewportSize(new Dimension(650, 70));
		//add to panel 3 
		p3.add(tickets);
		p3.add(numTick);
		p3.add(purchase);
		p3.add(date);
		date.setForeground(Color.blue);
		
		//add to main panel 
		add(panel);
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		
		//Action listeners for Button
		show.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sorter.setRowFilter(null);
			}
			
		});
		
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(dep.getSelectedItem().toString().equalsIgnoreCase(dest.getSelectedItem().toString())){
					JOptionPane.showMessageDialog(FlightReservationFrame.this, "Departure city and destination city must not be the same");
				}//end if 
				else{
					ArrayList filter = new ArrayList(); //Blank array 
					
					//Filter departure combo box input 
					filter.add(RowFilter.regexFilter("(?i)" + dep.getSelectedItem()));
					//Filter destination combo box input 
					filter.add(RowFilter.regexFilter("(?i)" + dest.getSelectedItem()));	
					
					//Filter both 
					sorter.setRowFilter(RowFilter.andFilter(filter));
				}
				
				}
			
		});
		
		numTick.addKeyListener(new KeyAdapter(){
			
			public void keyTyped(KeyEvent e){
				char input = e.getKeyChar();
				if(input < '0' || input >'9'){
					JOptionPane.showMessageDialog(FlightReservationFrame.this, "Please enter a number only" );
					e.consume();
				}
			}
		});
		
		purchase.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int yes=0;
				JOptionPane.showConfirmDialog(FlightReservationFrame.this, "Confirm purchase");
				numTick.setText(null);
				
				//if( yes == JOptionPane.YES_OPTION ){
					//JOptionPane.showMessageDialog(FlightReservationFrame.this, "Putchase confirmed");
					//numTick.setText(null);
				//}
				//else{
					//JOptionPane.showMessageDialog(FlightReservationFrame.this, "Cannot attempt to purchase 0 tickets, try again");
					
				//}
				
			}
			
		});
		
		//set frame properties
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setTitle("Flight Reservation");
		setSize(700,230);
		
	}//end 
	
	
	public static void main(String[] args) {
		new FlightReservationFrame();

	}//End main 

}//End class 
