package assingment1;

import javax.swing.table.AbstractTableModel;

public class FlightReservationModel extends AbstractTableModel {
	
	private String[] columnNames = {"Departure",
            "Destination",
            "Flight Num",
            "Number of Txx",
            "First Class Available"};
	
	private Object[][] data = {
		    {"Dublin", "Copenhagen", "SK538", new Integer(200), new Boolean(false)},
		    {"Dublin", "Oslo", "DY1363", new Integer(27), new Boolean(false)},
		    {"San Francisco", "Dublin", "EI147", new Integer(30), new Boolean(true)},
		    {"Edinburgh", "Dublin", "EI147", new Integer(50), new Boolean(false)},
		    {"New York", "Dublin", "EI109", new Integer(40), new Boolean(true)},
		    
		    
		    
		};
	
	

	public FlightReservationModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return data[rowIndex][columnIndex];
	}
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	@Override 
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = value;
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int cloumnIndex) {
		if(cloumnIndex <3) {
			return false;
		}//End if 
		else {
			return true;
		}//End else 
			
	}
}
