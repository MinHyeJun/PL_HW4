import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class CustomerTable implements Runnable
{
	private HashMap<String, Customer> customerList;
	
	private Customer tmpCustomer;
	private int managingMode;
	
	private ManagingCustomerPanel panel;
	
	public CustomerTable(ManagingCustomerPanel panel)
	{
		customerList = new HashMap<>();
		this.panel = panel;
	}
	
	private int getSize()
	{
		return customerList.size();
	}
	
	private void addCustomer()
	{
		if(!customerList.containsKey(tmpCustomer.getCustomNum()))
			customerList.put(tmpCustomer.getCustomNum(), tmpCustomer);
		
		JOptionPane.showMessageDialog(null, getSize());
	}

	private void removeCustomer()
	{
		if(customerList.containsKey(tmpCustomer.getCustomNum()))
			customerList.remove(tmpCustomer.getCustomNum());
			
		JOptionPane.showMessageDialog(null, getSize());
	}
	
	private void searchCustomer()
	{
		if(customerList.containsKey(tmpCustomer.getCustomNum()))
		{
			tmpCustomer = customerList.get(tmpCustomer.getCustomNum());
		
			panel.getTextFieldNum().setText(tmpCustomer.getCustomNum());
			panel.getTextFieldName().setText(tmpCustomer.getName());
			panel.getTextFieldPhoneNum().setText(tmpCustomer.getPhoneNum());
			panel.getTextFieldDate().setText(tmpCustomer.getDate());
		}
	}
	
	private void saveCustomer()
	{
		
	}
	
	private void loadCustomer()
	{
		
	}
	
	public void setManagingMode(int mode)
	{
		managingMode = mode;
	}
	
	public void setCustomerInfo(String name, String phoneNum, String customNum, String addDate)
	{
		tmpCustomer = new Customer(name, phoneNum, customNum, addDate);
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		switch(managingMode)
		{
			case 1:
				addCustomer();
				break;
			case 2:
				searchCustomer();
				break;
			case 3:
				removeCustomer();
				break;
		}
	}
}





class Customer
{
	private String name;
	private String phoneNum;
	private String customNum;
	private String addDate;
	
	public Customer(String name, String phoneNum, String customNum, String addDate)
	{
		this.name = name;
		this.phoneNum = phoneNum;
		this.customNum = customNum;
		this.addDate = addDate;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPhoneNum()
	{
		return phoneNum;
	}
	
	public String getCustomNum()
	{
		return customNum;
	}
	
	public String getDate()
	{
		return addDate;
	}
}
