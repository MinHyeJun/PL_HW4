import java.util.ArrayList;

public class CustomerTable implements Runnable
{
	private ArrayList<Customer> customerList;
	private int managingMode;
	
	public CustomerTable()
	{
		customerList = new ArrayList<>();
	}
	
	public int getSize()
	{
		return customerList.size();
	}
	
	public void addCustomer(String name, String phoneNum, String customNum, String addDate)
	{
		Customer newCustomer = new Customer(name, phoneNum, customNum, addDate);
		
		if(!customerList.contains(newCustomer))
			customerList.add(newCustomer);
	}

	public void removeCurstomer(String name, String phoneNum, String customNum, String addDate)
	{
		Customer newCustomer = new Customer(name, phoneNum, customNum, addDate);
		
		for(int i = 0; i < getSize(); i++)
		{
			if(customerList.get(i).equals(newCustomer))
			{
				customerList.remove(i);
				break;
			}
		}
	}
	
	public Customer searchCustomer(String customNum)
	{
		Customer output = null;
		
		for(int i = 0; i < getSize(); i++)
		{
			if(customerList.get(i).getCustomNum().equals(customNum))
			{
				output = customerList.get(i);
				break;
			}
		}
		return output;
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

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
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
	
	public String getCustomNum()
	{
		return customNum;
	}
}
