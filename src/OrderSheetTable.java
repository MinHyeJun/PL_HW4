import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		try
		{
			if(tmpOrder.getDate().equals(""))
				throw new WrongInputDataException("³¯Â¥¸¦ ±âÀçÇØÁÖ½Ê½Ã¿À.");
				
			orderList.add(tmpOrder);
		
			JOptionPane.showMessageDialog(null, "ÁÖ¹®ÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.", "ÁÖ¹® ¼º°ø", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "ÁÖ¹® ½ÇÆÐ", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void removeOrderSheet()
	{
		try
		{
			if(tmpOrder.getDate().equals(""))
				throw new WrongInputDataException("³¯Â¥¸¦ ±âÀçÇØÁÖ½Ê½Ã¿À.");
			
			int index;
		
			for(index = 0; index < getSize(); index++)
			{
				if(orderList.get(index).equals(tmpOrder))
					break;
			}
		
			if(index >= getSize())
					throw new NotExistDataException("»èÁ¦ÇÒ ÁÖ¹® Á¤º¸°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
			else
				orderList.remove(index);
			
			JOptionPane.showMessageDialog(null, "ÁÖ¹®ÀÌ Ãë¼ÒµÇ¾ú½À´Ï´Ù.", "Ãë¼Ò ¼º°ø", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ãë¼Ò ½ÇÆÐ", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void setOrderInfo(String date, String customNum, int menu)
	throws IllegalInputFormException, WrongCharactersException
	{
		String form;
		Pattern pattern;
		Matcher matcher;
		
		form = "[^0-9|/]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(date);
			
		if(matcher.find())
			throw new IllegalInputFormException("Àß¸øµÈ ³¯Â¥ Çü½ÄÀÔ´Ï´Ù.");
		
		form = "[^¤¡-¤¾°¡-ÆRa-zA-Z0-9]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(customNum);
		
		if(matcher.find())
			throw new WrongCharactersException("Æ¯¼ö¹®ÀÚ°¡ ÀÔ·ÂµÇ¾ú½À´Ï´Ù.");
		
		if(customNum.equals(""))
			tmpOrder = new OrderSheet(date, "GUEST", menu);
		else
			tmpOrder = new OrderSheet(date, customNum, menu);
	}
	
	public void setManagingMode(int mode)
	{
		managingMode = mode;
	}
	
	private boolean isWin()
	{
		int cnt = 0;
		
		for(int i = 0; i < getSize(); i++)
		{
			if(orderList.get(i).getCustomNum().equals(tmpOrder.getCustomNum()))
				cnt++;
		}
		
		if((cnt > 0) && (cnt % 3 == 0))
			return true;
		else
			return false;
	}
	
	private void giveCoupon()
	{
		String output;
		if(isWin())
		{
			output = tmpOrder.getCustomNum() + "¹ø °í°´´Ô\n" + "¹«·áÄíÆùÀÌ ¹è¼ÛµÇ¾ú½À´Ï´Ù.";
			JOptionPane.showMessageDialog(null, output, "ÄíÆù Áö±Þ", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		switch(managingMode)
		{
			case 1:
				addOrderSheet();
				giveCoupon();
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