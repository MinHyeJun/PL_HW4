import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class ManagingCustomerPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerTable customerTab;
	private OrderSheetTable orderTab;
	
	private JLabel labelNum;
	private JLabel labelName;
	private JLabel labelPhoneNum;
	private JLabel labelDate;
	
	private JTextField textNum;
	private JTextField textName;
	private JTextField textPhoneNum;
	private JTextField textDate;
	
	private JButton btnAdd;
	private JButton btnSearch;
	private JButton btnRemove;
	
	public ManagingCustomerPanel(CustomerTable customerTab, OrderSheetTable orderTab)
	{
		this.customerTab = customerTab;
		this.orderTab = orderTab;
		setLayout(null);
		// 라벨 생성
		labelNum = new JLabel("고객번호");
		labelNum.setBounds(130,50,100,30);
		labelName = new JLabel("고 객 명");
		labelName.setBounds(130,100,100,30);
		labelPhoneNum = new JLabel("전화번호");
		labelPhoneNum.setBounds(130,150,100,30);
		labelDate = new JLabel("생    일");
		labelDate.setBounds(130,200,100,30);		
		
		// 텍스트 필드 생성
		textNum = new JTextField();
		textNum.setBounds(200, 50, 200, 30);
		textName = new JTextField();
		textName.setBounds(200, 100, 200, 30);
		textPhoneNum = new JTextField();
		textPhoneNum.setBounds(200, 150, 200, 30);
		textDate = new JTextField();
		textDate.setBounds(200, 200, 200, 30);
		
		// 버튼 생성
		btnAdd = new JButton("고객등록");
		btnAdd.setBounds(50,300,100,30);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				startTask(1);
			}
		});
		
		btnSearch = new JButton("고객검색");
		btnSearch.setBounds(250,300,100,30);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startTask(2);
			}
		});
		btnRemove = new JButton("고객삭제");
		btnRemove.setBounds(450,300,100,30);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startTask(3);
			}
		});
		
		add(labelNum);
		add(labelName);
		add(labelPhoneNum);
		add(labelDate);
		add(textNum);
		add(textName);
		add(textPhoneNum);
		add(textDate);
		add(btnAdd);
		add(btnSearch);
		add(btnRemove);
	}
	
	private void startTask(int mode)
	{
		try
		{
			customerTab.setManagingMode(mode);
			customerTab.setCustomerInfo(textName.getText(), textPhoneNum.getText(), textNum.getText(), textDate.getText());
		
			Thread thread = new Thread(customerTab);
			thread.start();
			
			saveDatas();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "입력 확인", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public JTextField getTextFieldNum()
	{
		return textNum;
	}
	
	public JTextField getTextFieldName()
	{
		return textName;
	}
	
	public JTextField getTextFieldPhoneNum()
	{
		return textPhoneNum;
	}
	
	public JTextField getTextFieldDate()
	{
		return textDate;
	}
	
	public void loadCustomerData(String line)
	{
		customerTab.loadCustomer(line);
	}
	
	private void saveDatas()
	{
		try
		{
			File file = new File("custom.txt");
			BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file));
			
			customerTab.saveCustomer(file, bufWriter);
			bufWriter.write("=====");
			bufWriter.newLine();
			orderTab.saveOrder(file, bufWriter);
			bufWriter.close();
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
		
	}
	
}

