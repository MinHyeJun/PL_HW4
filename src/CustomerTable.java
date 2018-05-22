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
				throw new WrongInputDataException("��� �׸��� �������ֽʽÿ�.");
			
			if(!contains(tmpCustomer))
			{
				customerList.add(tmpCustomer);
				JOptionPane.showMessageDialog(null, "�������� ��ϵǾ����ϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				customerList.get(getIndex(tmpCustomer)).setName(tmpCustomer.getName());
				customerList.get(getIndex(tmpCustomer)).setDate(tmpCustomer.getDate());
				customerList.get(getIndex(tmpCustomer)).setPhoneNum(tmpCustomer.getPhoneNum());
				
				JOptionPane.showMessageDialog(null, "�������� �����Ǿ����ϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "��� ����", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void removeCustomer()
	{
		try
		{
			if(tmpCustomer.getCustomNum().equals(""))
				throw new WrongInputDataException("�� ��ȣ�� �������ֽʽÿ�.");
			
			if(contains(tmpCustomer))
				customerList.remove(getIndex(tmpCustomer));
			else
				throw new NotExistDataException("������ �� ������ �������� �ʽ��ϴ�.");
			
			JOptionPane.showMessageDialog(null, "�������� �����Ǿ����ϴ�.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "���� ����", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void searchCustomer()
	{
		try
		{
			if(tmpCustomer.getCustomNum().equals(""))
				throw new WrongInputDataException("�� ��ȣ�� �������ֽʽÿ�.");
			
			if(contains(tmpCustomer))
			{
				tmpCustomer = customerList.get(getIndex(tmpCustomer));
		
				panel.getTextFieldNum().setText(tmpCustomer.getCustomNum());
				panel.getTextFieldName().setText(tmpCustomer.getName());
				panel.getTextFieldPhoneNum().setText(tmpCustomer.getPhoneNum());
				panel.getTextFieldDate().setText(tmpCustomer.getDate());
				
				JOptionPane.showMessageDialog(null, "�������� �˻��Ǿ����ϴ�.", "�˻� ����", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				throw new NotExistDataException("�˻��� �� ������ �������� �ʽ��ϴ�.");
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "�˻� ����", JOptionPane.WARNING_MESSAGE);
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
					bufWriter.newLine();
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
		
		form = "[^��-����-�Ra-zA-Z0-9]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(name);
		
		if(matcher.find())
			throw new WrongCharactersException("Ư�����ڰ� �ԷµǾ����ϴ�.");
		else if(name.length() >= 10)
			throw new StringOverFlowException("�Էµ� �̸��� 10�ڸ� �ѽ��ϴ�.");
		
		matcher = pattern.matcher(customNum);
		if(matcher.find())
			throw new WrongCharactersException("Ư�����ڰ� �ԷµǾ����ϴ�.");
		
		form = "[^0-9|-]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(phoneNum);

		if(matcher.find())
			throw new IllegalInputFormException("�߸��� ��ȭ��ȣ �����Դϴ�.");
			
		form = "[^0-9|/]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(addDate);
			
		if(matcher.find())
			throw new IllegalInputFormException("�߸��� ��¥ �����Դϴ�.");
			
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
	
	public void setDate(String date)
	{
		this.addDate = date;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setPhoneNum(String phoneNum)
	{
		this.phoneNum = phoneNum;
	}
}
