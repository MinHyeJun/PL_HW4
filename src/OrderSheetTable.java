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
				throw new WrongInputDataException("��¥�� �������ֽʽÿ�.");
				
			orderList.add(tmpOrder);
		
			JOptionPane.showMessageDialog(null, "�ֹ��� �Ϸ�Ǿ����ϴ�.", "�ֹ� ����", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "�ֹ� ����", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void removeOrderSheet()
	{
		try
		{
			if(tmpOrder.getDate().equals(""))
				throw new WrongInputDataException("��¥�� �������ֽʽÿ�.");
			
			int index;
		
			for(index = 0; index < getSize(); index++)
			{
				if(orderList.get(index).equals(tmpOrder))
					break;
			}
		
			if(index >= getSize())
					throw new NotExistDataException("������ �ֹ� ������ �������� �ʽ��ϴ�.");
			else
				orderList.remove(index);
			
			JOptionPane.showMessageDialog(null, "�ֹ��� ��ҵǾ����ϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "��� ����", JOptionPane.WARNING_MESSAGE);
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
			throw new IllegalInputFormException("�߸��� ��¥ �����Դϴ�.");
		
		form = "[^��-����-�Ra-zA-Z0-9]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(customNum);
		
		if(matcher.find())
			throw new WrongCharactersException("Ư�����ڰ� �ԷµǾ����ϴ�.");
		
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
			output = tmpOrder.getCustomNum() + "�� ����\n" + "���������� ��۵Ǿ����ϴ�.";
			JOptionPane.showMessageDialog(null, output, "���� ����", JOptionPane.INFORMATION_MESSAGE);
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