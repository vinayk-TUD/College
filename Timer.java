package timer;

//Vinay Kumar C17474442

import javax.swing.*;
import javax.xml.stream.events.EndDocument;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

import net.miginfocom.swing.MigLayout;



public class Timer extends JFrame {
	
	// Interface components
	
	// Fonts to be used
	Font countdownFont = new Font("Arial", Font.BOLD, 20);
	Font elapsedFont = new Font("Arial", Font.PLAIN, 14);
	
	// Labels and text fields
	JLabel countdownLabel = new JLabel("Seconds remaining:");
	JTextField countdownField = new JTextField(15);
	JLabel elapsedLabel = new JLabel("Time running:");
	JTextField elapsedField = new JTextField(15);
	JButton startButton = new JButton("START");
	JButton pauseButton = new JButton("PAUSE");
	JButton stopButton = new JButton("STOP");
	
	// The text area and the scroll pane in which it resides
	JTextArea display;
	
	JScrollPane myPane;
	
	// These represent the menus
	JMenuItem saveData = new JMenuItem("Save data", KeyEvent.VK_S);
	JMenuItem displayData = new JMenuItem("Display data", KeyEvent.VK_D);
	
	JMenu options = new JMenu("Options");
	
	JMenuBar menuBar = new JMenuBar();
	
	// These booleans are used to indicate whether the START button has been clicked
	boolean started;
	
	// and the state of the timer (paused or running)
	boolean paused;
	
	// Number of seconds
	long totalSeconds = 0;
	long secondsToRun = 0;
	long secondsSinceStart = 0;
	
	// This is the thread that performs the countdown and can be started, paused and stopped
	TimerThread countdownThread;
	

	// Interface constructed
	Timer() {
		
		setTitle("Timer Application");
		
  	MigLayout layout = new MigLayout("fillx");
  	JPanel panel = new JPanel(layout);
  	getContentPane().add(panel);
  	
  	options.add(saveData);
  	options.add(displayData);
  	menuBar.add(options);
  	
  	panel.add(menuBar, "spanx, north, wrap");
  	MigLayout centralLayout = new MigLayout("fillx");
  	JPanel centralPanel = new JPanel(centralLayout);
  	GridLayout timeLayout = new GridLayout(2,2);
  	JPanel timePanel = new JPanel(timeLayout);
  	
  	countdownField.setEditable(false);
  	countdownField.setHorizontalAlignment(JTextField.CENTER);
  	countdownField.setFont(countdownFont);
  	countdownField.setText("00:00:00");
  	
  	timePanel.add(countdownLabel);
  	timePanel.add(countdownField);

  	elapsedField.setEditable(false);
  	elapsedField.setHorizontalAlignment(JTextField.CENTER);
  	elapsedField.setFont(elapsedFont);
  	elapsedField.setText("00:00:00");
  	
  	timePanel.add(elapsedLabel);
  	timePanel.add(elapsedField);

  	centralPanel.add(timePanel, "wrap");
  	GridLayout buttonLayout = new GridLayout(1, 3);
  	JPanel buttonPanel = new JPanel(buttonLayout);
  	
  	buttonPanel.add(startButton);
  	buttonPanel.add(pauseButton, "");
  	buttonPanel.add(stopButton, "");
  	
  	centralPanel.add(buttonPanel, "spanx, growx, wrap");
  	panel.add(centralPanel, "wrap");
  	
  	display = new JTextArea(100,150);
      display.setMargin(new Insets(5,5,5,5));
      display.setEditable(false);
      
      JScrollPane myPane = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      panel.add(myPane, "alignybottom, h 100:320, wrap");
      
      JFileChooser jfc = new JFileChooser();
      // Initial state of system
      paused = false;
      started = false;
      
      // Allowing interface to be displayed
  	setSize(400, 500);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
      
      // TODO: SAVE: This method should allow the user to specify a file name to which to save the contents of the text area using a 
      // JFileChooser. You should check to see that the file does not already exist in the system.
      saveData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jfc.setCurrentDirectory((new File(System.getProperty("user.dir"))));
				File filechosen = jfc.getSelectedFile();
				//int retVal = jfc.showOpenDialog(Timer.this);
				
				/*if(filechosen.exists()){
					int option = JOptionPane.showConfirmDialog(null,"Continue with save and replace the existing file?", 
				            "Waring", JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE);
					
					if(option == JOptionPane.YES_OPTION){
						
						if(!display.getText().isEmpty() && filechosen.exists()){
							
							try {
								writeDataFile(jfc.getSelectedFile());
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							display.setText("");
							display.append("");
						
						}//end if 
						else{
							JOptionPane.showMessageDialog(Timer.this,"There is no data to save", "ERROR", JOptionPane.ERROR_MESSAGE); 
						}
				}//end if */
				
				/*if(file.exists()){
					int option = JOptionPane.showConfirmDialog(Timer.this, "This will replace the existing file, Continue? ", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(option ==0){
						try {
							writeDataFile(jfc.getSelectedFile());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}//end if 
				}//end if */
				
				if(!display.getText().isEmpty()){
					
				int retVal = jfc.showSaveDialog(Timer.this);
				
					try {
						writeDataFile(jfc.getSelectedFile());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					display.setText("");
					display.append("");
				}//end if
				else{
					JOptionPane.showMessageDialog(Timer.this,"There is no data to save", "ERROR", JOptionPane.ERROR_MESSAGE); 
				}
				
			}});

      
      // TODO: DISPLAY DATa: This method should retrieve the contents of a file representing a previous report using a JFileChooser.
      // The result should be displayed as the contents of a dialog object.
      //Display Button 
      displayData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File fileChosen;
				jfc.setCurrentDirectory((new File(System.getProperty("user.dir"))));
				int retVal = jfc.showOpenDialog(Timer.this);
				int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
				dialogButton = JOptionPane.showConfirmDialog(Timer.this,
			"This will replace any existing data on the screen.\n Are you sure sure you want to do this?","Select an Option", dialogButton);
				
				
				if(dialogButton == JOptionPane.YES_OPTION) {
					fileChosen = jfc.getSelectedFile();
					 try {
						 JTextArea filedisplay = new JTextArea(10,25);
						 FileReader fileReader = new FileReader(fileChosen);
						 BufferedReader bufferReader = new BufferedReader(fileReader);
						 filedisplay.read(bufferReader, null);
						 JOptionPane.showMessageDialog(null, new JScrollPane(filedisplay), "Data", JOptionPane.INFORMATION_MESSAGE);
						 bufferReader.close();
						 //fileChosen = jfc.getSelectedFile();
						display.append(readDataFile(fileChosen));
						//display.append(fileChosen);
						
					} catch (ClassNotFoundException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					 if(dialogButton == JOptionPane.NO_OPTION ||dialogButton == JOptionPane.CANCEL_OPTION ) {}
				}//end if 
				
			}
		});
      
      
      // TODO: START: This method should check to see if the application is already running, and if not, launch a TimerThread object.
		// If the application is running, you may wish to ask the user if the existing thread should be stopped and a new thread started.
      // It should begin by launching a TimerDialog to get the number of seconds to count down, and then pass that number of seconds along
		// with the seconds since the start (0) to the TimerThread constructor.
		// It can then display a message in the text area stating how long the countdown is for.
      startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// if already running 
				if(started){
					int a = JOptionPane.showConfirmDialog(Timer.this, "Restart Timer?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
					if(a == JOptionPane.YES_OPTION){
						//stopp thread
						countdownThread.stop();
						//display the times 
						display();
						//Launch a new dialog as start is pressed 
						TimerDialog timerDialog = new TimerDialog(Timer.this, 0, true);
						//launch new thread 
						countdownThread = new TimerThread(countdownField, elapsedField, timerDialog.getSeconds(), secondsSinceStart);
						Thread thread = new Thread(countdownThread);
						thread.start();
						display.append("\nTimer restarted at : " + countdownThread.convertToHMSString(countdownThread.getCountdownSeconds()) + "\n" );
					}//end if 
				}//end if 
				
				///timer not already running 
				else {
					//Launch a new dialog as start is pressed 
					TimerDialog timerDialog = new TimerDialog(Timer.this, 0, true);
					//launch new thread 
					countdownThread = new TimerThread(countdownField, elapsedField, timerDialog.getSeconds(), secondsSinceStart);
					Thread thread = new Thread(countdownThread);
					thread.start();
					display.append("\nTimer started at : " + countdownThread.convertToHMSString(countdownThread.getCountdownSeconds()) + "\n" );
					
					started = true;
				}//end else 
			}
		});
      
      // TODO: PAUSE: This method should call the TimerThread object's pause method and display a message in the text area
      // indicating whether this represents pausing or restarting the timer.
      pauseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!started){
					JOptionPane.showMessageDialog(timePanel, "No time set");
				}//End if 
				
				if(!paused){
					display.append("\nTimer paused at : " + countdownThread.convertToHMSString(countdownThread.getCountdownSeconds()) + "\n" );
					countdownThread.pause();
					pauseButton.setText("Resume");
					paused =true;
				}//End if 
				else{
					display.append("Timer resumed at : " + countdownThread.getElapsedSeconds() + "seconds \n");
					countdownThread.pause();
					pauseButton.setText("Pause");
					paused = false;
				}//End else 
				
			}
		});
      
      // TODO: STOP: This method should stop the TimerThread object and use appropriate methods to display the stop time
      // and the total amount of time remaining in the countdown (if any).
      stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!started){
					return;
				}
				countdownThread.stop();
				started = false;
				display.append("\nTimer stopped at : " + countdownThread.getElapsedSeconds()+ "seconds" + 
						"\nRemaining time : " + countdownThread.getCountdownSeconds()+ "seconds");
				//reset();
				
			}
		});
  	
	}//End Timer 
	
	public void display(){
		display.append("Timer stopped at : " + countdownThread.getElapsedSeconds() + "seconds");
		display.append("\nRemaining time : " + countdownThread.getCountdownSeconds() + " seconds");
	}
	public void reset(){
		elapsedField.setText("00:00:00");
		countdownField.setText("00:00:00");
	}//end rest 
	
	// TODO: These methods can be used in the action listeners above.
	public synchronized void writeDataFile(File f) throws IOException, FileNotFoundException {
		/*FileOutputStream fileStream = null;
		ObjectOutputStream objectStream = null;
		try {
			//open stream
			fileStream =  new FileOutputStream(f);
			objectStream = new ObjectOutputStream(fileStream);
			//write to stream
			objectStream.writeObject(display.getText());

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		catch (IOException e) {
			
			e.printStackTrace();
		}
		finally
		{
			
			try {
				objectStream.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}*/
		/*ObjectOutputStream output;
		output = null;
		try{
			FileOutputStream fileout = new FileOutputStream(f);
			output = new ObjectOutputStream(fileout);
			output.writeObject(display.getText());
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			try{
				output.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}*/
		
		/*File file = f;
		BufferedWriter bWriter = null;
		try
		{
		    bWriter = new BufferedWriter( new FileWriter(file));
		    bWriter.write(display.getText());

		}
		catch ( IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
		    try
		    {
		        if ( bWriter != null)
		        bWriter.close();
		    }
		    catch ( IOException e)
		    {
		    }
		} */
		
		/*File file = f;
		String fileContent = display.getText();
	     
	    BufferedWriter writer = new BufferedWriter(new FileWriter(f));
	    writer.write(fileContent);
	    writer.close();*/
		
		FileOutputStream fileStream = null;
		ObjectOutputStream objectStream = null;
		String result = null;
		try {
			//open stream
			fileStream =  new FileOutputStream(f);
			objectStream = new ObjectOutputStream(fileStream);
			//write to stream
			objectStream.writeObject(display.getText());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
				try {
					objectStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}//End write to file 
	
	
	// TODO: These methods can be used in the action listeners above.
	public synchronized String readDataFile(File f) throws IOException, ClassNotFoundException {
		
		/*String result = new String();
		result = null;
		
		try{
			FileReader fileReader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((result = bufferedReader.readLine()) != null){
				System.out.println(result);
			}//end while 
			bufferedReader.close();
		} catch(FileNotFoundException e){
			System.out.println("Unable to open file");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		return result; */
		/*File file = f;
		String result = new String();
		result = null;
		BufferedReader bReader = null;
		FileReader fReader;
		
		try{
			fReader = new FileReader(file);
			bReader = new BufferedReader(fReader);
			result = bReader.readLine();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			try{
				bReader.close();
			}catch(IOException e ){
				e.printStackTrace();
			}
		} 
		return result;*/
		
		File file = f;
		FileReader fileR = new FileReader(f);
		BufferedReader bReader = new BufferedReader(fileR);
		
		String text = "";
		String line = bReader.readLine();
		while (line != null){
		text+= line;
		line = bReader.readLine();
		
		}//end whille
		
		bReader.close();
		display.append(text);
		return text;
	
	}//End read from file 

  public static void main(String[] args) {

      Timer timer = new Timer();

  }//End main 

}//End class
