import java.util.ArrayList;

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
		if(!orderList.contains(tmpOrder))
			orderList.add(tmpOrder);
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
}