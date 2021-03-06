import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class ManagingOrderPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerTable customerTab;
	private OrderSheetTable orderTab;
	private SalesTable salesTab;
	
	private JLabel labelDate;
	private JLabel labelNum;
	private JLabel labelMenu;
	
	private JTextField textDate;
	private JTextField textNum;
	private JComboBox<String> comboMenu;
	private String[] menuList = {"���", "������", "����", "�", "Ƣ��"};
	
	private JButton btnOrder;
	private JButton btnCancel;
	
	public ManagingOrderPanel(CustomerTable customerTab, OrderSheetTable orderTab, SalesTable salesTab)
	{
		this.customerTab = customerTab;
		this.orderTab = orderTab;
		this.salesTab = salesTab;
		setLayout(null);
		
		//�� ����
		labelDate = new JLabel("��    ¥");
		labelDate.setBounds(130,50,100,30);
		labelNum = new JLabel("������ȣ");
		labelNum.setBounds(130,100,100,30);
		labelMenu = new JLabel("��    ��");
		labelMenu.setBounds(130,150,100,30);
		
		textDate = new JTextField();
		textDate.setBounds(200, 50, 200, 30);
		textNum = new JTextField();
		textNum.setBounds(200, 100, 200, 30);
		comboMenu = new JComboBox<>(menuList);
		comboMenu.setBounds(200, 150, 200, 30);
		
		btnOrder = new JButton("�ֹ�");
		btnOrder.setBounds(150,300,100,30);
		btnOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startTask(1);
			}
		});
		btnCancel = new JButton("�ֹ����");
		btnCancel.setBounds(350,300,100,30);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startTask(2);
			}
		});
		
		add(labelDate);
		add(labelNum);
		add(labelMenu);
		add(textDate);
		add(textNum);
		add(comboMenu);
		add(btnOrder);
		add(btnCancel);
	}
	
	public void startTask(int mode)
	{
		try
		{
			if(!customerTab.contains(textNum.getText()))
				throw new Exception("��ϵ��� ���� ������ȣ�Դϴ�.");
			
			orderTab.setManagingMode(mode);
			orderTab.setOrderInfo(textDate.getText(), textNum.getText(), comboMenu.getSelectedIndex());
		
			Thread thread = new Thread(orderTab);
			thread.start();

			saveDatas();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "�Է� Ȯ��", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void loadOrderData(String line)
	{
		orderTab.loadOrder(line);
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
