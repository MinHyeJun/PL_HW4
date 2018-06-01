import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;

public class ManagingProgram extends JFrame
{
	public final static int GIMBAP = 0;
	public final static int TTEOKBOKKI = 1;
	public final static int SUNDAE = 2;
	public final static int FISH_CAKE = 3;
	public final static int FRIED = 4;
	
	private final static int x1 = 500;
	private final static int y1 = 200;
	private final static int x2 = 700;
	private final static int y2 = 500;
	
	private static final long serialVersionUID = -711163588504124217L;

	
	public ManagingOrderPanel orderPanel = null;
	public ManagingCustomerPanel customPanel = null;
	public CheckingSalesPanel checkingPanel = null;
	
	public ManagingProgram() {
		
		// 扩档快 芒 积己
		super("Managing Customer Program");
		setBounds(x1, y1, x2, y2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CustomerTable customerTab = new CustomerTable();
		OrderSheetTable orderTab = new OrderSheetTable();
		SalesTable salesTab = new SalesTable(customerTab, orderTab);
		
		customPanel = new ManagingCustomerPanel(customerTab, orderTab, salesTab);
		orderPanel = new ManagingOrderPanel(customerTab, orderTab, salesTab);
		checkingPanel = new CheckingSalesPanel(salesTab);
		
		customerTab.setManagingCustomerPanel(customPanel);
		orderTab.setManagingOrderPanel(orderPanel);
		salesTab.setPanel(checkingPanel);
		
		loadDatas("custom.txt");
		
		JTabbedPane jTab = new JTabbedPane();
		add(jTab);
		jTab.addTab("林巩包府", orderPanel);
		jTab.addTab("绊按包府", customPanel);
		jTab.addTab("概免炼雀", checkingPanel);

		setVisible(true);
	}
	
	private void loadDatas(String fileName)
	{
		try
		{
			File file = new File(fileName);
			BufferedReader bufReader = new BufferedReader(new FileReader(file));
			boolean CustomerOrOrder = false;
			String line = "";
			
			while((line = bufReader.readLine()) != null)
			{
				if(line.contains("====="))
				{
					CustomerOrOrder = true;
					continue;
				}
				
				if(CustomerOrOrder == false)
					customPanel.loadCustomerData(line);
				else
					orderPanel.loadOrderData(line);
			}
			
			bufReader.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new ManagingProgram();
	}
}

class IllegalInputFormException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalInputFormException() { }
	
	public IllegalInputFormException(String message) {
		super(message);
	}
}

class WrongCharactersException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongCharactersException() { }
	
	public WrongCharactersException(String message) {
		super(message);
	}
}

class StringOverFlowException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StringOverFlowException() { }
	
	public StringOverFlowException(String message) {
		super(message);
	}
}

class NotExistDataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotExistDataException() { }
	
	public NotExistDataException(String message) {
		super(message);
	}
}

class WrongInputDataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongInputDataException() { }
	
	public WrongInputDataException(String message) {
		super(message);
	}
}