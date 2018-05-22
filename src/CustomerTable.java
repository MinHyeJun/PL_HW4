import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		try
		{
			if(tmpCustomer.getCustomNum().equals("") || tmpCustomer.getDate().equals("")
					|| tmpCustomer.getName().equals("") || tmpCustomer.getPhoneNum().equals(""))
				throw new WrongInputDataException("모든 항목을 기재해주십시오.");
			
			if(!contains(tmpCustomer))
			{
				customerList.add(tmpCustomer);
				JOptionPane.showMessageDialog(null, "고객정보가 등록되었습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				throw new ExistDataException("이미 존재하는 고객번호입니다.");
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "등록 실패", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void removeCustomer()
	{
		try
		{
			if(tmpCustomer.getCustomNum().equals(""))
				throw new WrongInputDataException("고객 번호를 기재해주십시오.");
			
			if(contains(tmpCustomer))
				customerList.remove(getIndex(tmpCustomer));
			else
				throw new NotExistDataException("삭제할 고객 정보가 존재하지 않습니다.");
			
			JOptionPane.showMessageDialog(null, "고객정보가 삭제되었습니다.", "삭제 성공", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "삭제 실패", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void searchCustomer()
	{
		try
		{
			if(tmpCustomer.getCustomNum().equals(""))
				throw new WrongInputDataException("고객 번호를 기재해주십시오.");
			
			if(contains(tmpCustomer))
			{
				tmpCustomer = customerList.get(getIndex(tmpCustomer));
		
				panel.getTextFieldNum().setText(tmpCustomer.getCustomNum());
				panel.getTextFieldName().setText(tmpCustomer.getName());
				panel.getTextFieldPhoneNum().setText(tmpCustomer.getPhoneNum());
				panel.getTextFieldDate().setText(tmpCustomer.getDate());
				
				JOptionPane.showMessageDialog(null, "고객정보가 검색되었습니다.", "검색 성공", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				throw new NotExistDataException("검색할 고객 정보가 존재하지 않습니다.");
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "검색 실패", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void saveCustomer()
	{
		try
		{
			String output;
			File file = new File("custom.txt");
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
			File file = new File("custom.txt");
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
	throws IllegalInputFormException, WrongCharactersException, StringOverFlowException
	{
		String form;
		Pattern pattern;
		Matcher matcher;
		
		form = "[^ㄱ-ㅎ가-힣a-zA-Z0-9]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(name);
		
		if(matcher.find())
			throw new WrongCharactersException("특수문자가 입력되었습니다.");
		else if(name.length() >= 10)
			throw new StringOverFlowException("입력된 이름이 10자를 넘습니다.");
		
		matcher = pattern.matcher(customNum);
		if(matcher.find())
			throw new WrongCharactersException("특수문자가 입력되었습니다.");
		
		form = "[^0-9|-]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(phoneNum);

		if(matcher.find())
			throw new IllegalInputFormException("잘못된 전화번호 형식입니다.");
			
		form = "[^0-9|/]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(addDate);
			
		if(matcher.find())
			throw new IllegalInputFormException("잘못된 날짜 형식입니다.");
			
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
