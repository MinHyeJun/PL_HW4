import java.util.ArrayList;

public class OrderSheetTable implements Runnable
{
	private ArrayList<OrderSheet> orderList;
	private int managingMode;
	
	public OrderSheetTable()
	{
		orderList = new ArrayList<>();
	}
	
	public int getSize()
	{
		return orderList.size();
	}
	
	public void addOrderSheet(String date, String customNum, int menu)
	{
		OrderSheet newOrder = new OrderSheet(date, customNum, menu);
		
		if(!orderList.contains(newOrder))
			orderList.add(newOrder);
	}
	
	public void removeOrderSheet(String date, String customNum, int menu)
	{
		OrderSheet newOrder = new OrderSheet(date, customNum, menu);
		
		for(int i = 0; i < getSize(); i++)
		{
			if(orderList.get(i).equals(newOrder))
			{
				orderList.remove(i);
				break;
			}
		}
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
}