package timer;

// Vinay Kumar - C17474442
import java.awt.*;
import java.awt.event.*;
import java.security.acl.Owner;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;


public class TimerDialog extends JDialog {
	
	// Represents the number of seconds that the countdown will be performed for.
	private long seconds;
	private Timer owner;
	// Menu components.
	JTextField hourField, minField, secField;
	JLabel hourLabel, minLabel, secLabel;
	JButton startButton = new JButton("START");

	public TimerDialog(Timer owner, long seconds, boolean modality) {
		super(owner, modality);
		this.owner = owner;
		this.seconds = seconds;
		
		initComponents();
	}//end constructor 
	
	// Sets up display.
	private void initComponents() {
		setTitle("Initialise Timer");	
		setLayout(new BorderLayout());
		
		Font displayFont = new Font("Arial", Font.BOLD, 16);
		Font labelFont = new Font("Arial", Font.BOLD, 12);
		
		JPanel displayPanel = new JPanel(new GridLayout(1,3));
				
		JPanel hourPanel = new JPanel(new BorderLayout());
		hourField = new JTextField(5);
		hourField.setHorizontalAlignment(JTextField.CENTER);
		hourField.setFont(displayFont);
		hourField.setText("00");
		hourLabel = new JLabel("Hours");
		hourLabel.setHorizontalAlignment(JTextField.CENTER);
		hourLabel.setFont(labelFont);
		hourPanel.add(hourField, BorderLayout.CENTER);
		hourPanel.add(hourLabel, BorderLayout.SOUTH);
		
		displayPanel.add(hourPanel);
		
		JPanel minPanel = new JPanel(new BorderLayout());
		minField = new JTextField(5);
		minField.setHorizontalAlignment(JTextField.CENTER);
		minField.setFont(displayFont);
		minField.setText("00");
		minLabel = new JLabel("Minutes");
		minLabel.setHorizontalAlignment(JTextField.CENTER);
		minLabel.setFont(labelFont);
		minPanel.add(minField, BorderLayout.CENTER);
		minPanel.add(minLabel, BorderLayout.SOUTH);
		
		displayPanel.add(minPanel);
		
		JPanel secPanel = new JPanel(new BorderLayout());
		secField = new JTextField(5);
		secField.setHorizontalAlignment(JTextField.CENTER);
		secField.setFont(displayFont);
		secField.setText("00");
		secLabel = new JLabel("Seconds");
		secLabel.setHorizontalAlignment(JTextField.CENTER);
		secLabel.setFont(labelFont);
		secPanel.add(secField, BorderLayout.CENTER);
		secPanel.add(secLabel, BorderLayout.SOUTH);
		
		displayPanel.add(secPanel);
		
		add(displayPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(startButton);
		
		// TODO: This start action listener will be invoked when the start button is clicked.
		// It should take the values from the three text fields and try to convert them into integer values, and then check for NumberFormatExceptions 
		// and for the minute and second values between 0 and 59.
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Timer started");
				
				t();
				System.out.println(getSeconds());
			}
		});
		
		/*startButton.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e){
				char input = e.getKeyChar();
				
			}
		});*/
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		setSize(300, 150);
		setVisible(true);
		
	}
	
	public void t(){
		int min =0; 
		int sec = 0; 
		int hour = 0;
		
		try {
			sec = Integer.valueOf(secField.getText());
			min = Integer.valueOf(minField.getText());
			hour = Integer.valueOf(hourField.getText());
		} catch (NumberFormatException e) {
			//System.out.println("Only Numbers can be entered");
			JOptionPane.showMessageDialog(TimerDialog.this, "Only numbers can be entered", 
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}//end catch 
		
		if(sec> 59){
			JOptionPane.showMessageDialog(TimerDialog.this, "Seconds must be betwwon 0-59", 
					"Input Error", JOptionPane.ERROR_MESSAGE);
			secField.setText("00"); return;

		}//end if 
		
		if(min> 59){
			JOptionPane.showMessageDialog(TimerDialog.this, "Minutes must be betweenn 0-59", 
					"Input Error", JOptionPane.ERROR_MESSAGE);
			minField.setText("00"); return;
		}//end if 
		
		if(hour> 59){
			JOptionPane.showMessageDialog(TimerDialog.this, "Hours must be betwwon 0-59", 
					"Input Error", JOptionPane.ERROR_MESSAGE);
			hourField.setText("00"); return;
		}//end if 
		
		this.seconds = sec + (min*60)+ (hour*3600);
		dispose();
	}//end 
	
	public void setSeconds(long seconds){
		this.seconds = seconds;
	}
	
	public long getSeconds() {
		return (long)seconds;
		
		//return (Long.valueOf(hourField.getText())*60*60) + (Long.valueOf(minField.getText())*60)
			//	+ (Long.valueOf(secField.getText()));
	}//end 

}