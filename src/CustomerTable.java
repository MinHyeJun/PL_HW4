import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class CustomerTable implements Runnable
{
	private List<Customer> customerList;
	
	private Customer tmpCustomer;
	private int managingMode;
	
	private ManagingCustomerPanel panel;
	
	public CustomerTable(ManagingCustomerPanel panel)
	{
		customerList = new ArrayList<>();
		this.panel = panel;
		loadCustomer();
	}
	
	private int getSize()
	{
		return customerList.size();
	}
	
	private int getIndex(Customer newCustomer)
	{
		Customer current;
		
		for(int i = 0; i < getSize(); i ++)
		{
			current = customerList.get(i);
			
			if(current.getCustomNum().equals(newCustomer.getCustomNum()))
			{
				return i;
			}
		}
		return -1;
	}
	
	private boolean contains(Customer newCustomer)
	{
		return (getIndex(newCustomer) >= 0);
	}
	
	private void addCustomer()
	{
		if(!contains(tmpCustomer))
			customerList.add(tmpCustomer);
		
		JOptionPane.showMessageDialog(null, getSize());
	}

	private void removeCustomer()
	{
		if(contains(tmpCustomer))
			customerList.remove(getIndex(tmpCustomer));
			
		JOptionPane.showMessageDialog(null, getSize());
	}
	
	private void searchCustomer()
	{
		if(contains(tmpCustomer))
		{
			tmpCustomer = customerList.get(getIndex(tmpCustomer));
		
			panel.getTextFieldNum().setText(tmpCustomer.getCustomNum());
			panel.getTextFieldName().setText(tmpCustomer.getName());
			panel.getTextFieldPhoneNum().setText(tmpCustomer.getPhoneNum());
			panel.getTextFieldDate().setText(tmpCustomer.getDate());
		}
	}
	
	private void saveCustomer()
	{
		try
		{
			String output;
			File file = new File("customer.txt");
			BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file));
			
			if(file.isFile() && file.canWrite())
			{
				for(int i = 0; i < getSize(); i++)
				{
					output = customerList.get(i).getName() + "\t" + customerList.get(i).getPhoneNum() + "\t"
							+ customerList.get(i).getCustomNum() + "\t" + customerList.get(i).getDate();
					bufWriter.write(output);
				}
				bufWriter.close();
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
		
		
	}
	
	private void loadCustomer()
	{
		try
		{
			File file = new File("customer.txt");
			BufferedReader bufReader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = bufReader.readLine()) != null)
			{
				String[] unit = line.split("\t");
				Customer newCustomer = new Customer(unit[0], unit[1], unit[2], unit[3]);
				customerList.add(newCustomer);
			}
			tmpCustomer = null;
			bufReader.close();
		}
		catch(FileNotFoundException e)
		{
			
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
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
				saveCustomer();
				break;
			case 2:
				searchCustomer();
				break;
			case 3:
				removeCustomer();
				saveCustomer();
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
	private int orderCount;
	
	public Customer(String name, String phoneNum, String customNum, String addDate)
	{
		this.name = name;
		this.phoneNum = phoneNum;
		this.customNum = customNum;
		this.addDate = addDate;
		orderCount = 0;
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
