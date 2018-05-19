import java.util.ArrayList;

import javax.swing.JOptionPane;

public class OrderSheetTable implements Runnable
{
	private ArrayList<OrderSheet> orderList;
	private OrderSheet tmpOrder;
	private int managingMode;
	
	public OrderSheetTable()
	{
		orderList = new ArrayList<>();
	}
	
	private int getSize()
	{
		return orderList.size();
	}
	
	private void addOrderSheet()
	{
		orderList.add(tmpOrder);
		
		JOptionPane.showMessageDialog(null, getSize());
	}
	
	private void removeOrderSheet()
	{
		for(int i = 0; i < getSize(); i++)
		{
			if(orderList.get(i).equals(tmpOrder))
			{
				orderList.remove(i);
				break;
			}
		}
		
		JOptionPane.showMessageDialog(null, getSize());
	}
	
	public void setOrderInfo(String date, String customNum, int menu)
	{
		tmpOrder = new OrderSheet(date, customNum, menu);
	}
	
	public void setManagingMode(int mode)
	{
		managingMode = mode;
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		switch(managingMode)
		{
			case 1:
				addOrderSheet();
				break;
			case 2:
				removeOrderSheet();
				break;
		}
		
	}
}





class OrderSheet
{
	private String date;
	private String customNum;
	private int menu;
	
	public OrderSheet(String date, String customNum, int menu)
	{
		this.date = date;
		this.customNum = customNum;
		this.menu = menu;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getCustomNum()
	{
		return customNum;
	}
	
	public int getMenu()
	{
		return menu;
	}
	
	public boolean equals(OrderSheet input)
	{
		if(date.equals(input.getDate()) && customNum.equals(input.getCustomNum()) && menu == input.getMenu())
		{
			return true;
		}
		
		return false;
	}
}