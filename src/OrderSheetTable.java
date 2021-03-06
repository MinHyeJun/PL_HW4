import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class OrderSheetTable implements Runnable
{
	private ArrayList<OrderSheet> orderList;
	
	private OrderSheet tmpOrder;
	private int managingMode;
	
	private ManagingOrderPanel panel;
	
	SimpleDateFormat format;
	
	public OrderSheetTable()
	{
		orderList = new ArrayList<>();
	}
	
	public void setManagingOrderPanel(ManagingOrderPanel panel)
	{
		this.panel = panel;
	}
	
	public int getSize()
	{
		return orderList.size();
	}
	
	public OrderSheet getOrder(int index)
	{
		return orderList.get(index);
	}
	
	private void addOrderSheet()
	{
		try
		{
			if(tmpOrder.getDate().equals(""))
				throw new WrongInputDataException("날짜를 기재해주십시오.");
				
			orderList.add(tmpOrder);
		
			JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.", "주문 성공", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "주문 실패", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void removeOrderSheet()
	{
		try
		{
			if(tmpOrder.getDate().equals(""))
				throw new WrongInputDataException("날짜를 기재해주십시오.");
			
			int index;
		
			for(index = 0; index < getSize(); index++)
			{
				if(orderList.get(index).equals(tmpOrder))
					break;
			}
		
			if(index >= getSize())
					throw new NotExistDataException("삭제할 주문 정보가 존재하지 않습니다.");
			else
				orderList.remove(index);
			
			JOptionPane.showMessageDialog(null, "주문이 취소되었습니다.", "취소 성공", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "취소 실패", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void saveOrder(File file, BufferedWriter bufWriter) throws IOException
	{

		String output;
			
		if(file.isFile() && file.canWrite())
		{
				for(int i = 0; i < getSize(); i++)
			{
					output = orderList.get(i).getDate() + "\t" + orderList.get(i).getCustomNum() + "\t"
							+ orderList.get(i).getMenu();
					bufWriter.write(output);
					bufWriter.newLine();
			}
		}
	}
	
	public void loadOrder(String line)
	{
		String[] unit = line.split("\t");
		OrderSheet newOrder = new OrderSheet (unit[0], unit[1], Integer.parseInt(unit[2]));
		orderList.add(newOrder);
	}
	
	public void setOrderInfo(String date, String customNum, int menu)
	throws IllegalInputFormException, WrongCharactersException
	{
		String form;
		Pattern pattern;
		Matcher matcher;
		
		form = "^\\d{4}/\\d{2}/\\d{2}$";
		boolean isOK = Pattern.matches(form,date);
			
		if(!isOK)
			throw new IllegalInputFormException("잘못된 날짜 형식입니다.");
		
		form = "[^ㄱ-ㅎ가-힣a-zA-Z0-9]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(customNum);
		
		if(matcher.find())
			throw new WrongCharactersException("특수문자가 입력되었습니다.");
		
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
			output = tmpOrder.getCustomNum() + "번 고객님\n" + "무료쿠폰이 배송되었습니다.";
			JOptionPane.showMessageDialog(null, output, "쿠폰 지급", JOptionPane.INFORMATION_MESSAGE);
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