import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ManagingOrderPanel extends JPanel
{
	private OrderSheetTable orderTab;
	
	private JLabel labelDate;
	private JLabel labelNum;
	private JLabel labelMenu;
	
	private JTextField textDate;
	private JTextField textNum;
	private JComboBox comboMenu;
	private String[] menuList = {"±è¹ä", "¶±ººÀÌ", "¼ø´ë", "¾î¹¬", "Æ¢±è"};
	
	private JButton btnOrder;
	private JButton btnCancel;
	
	public ManagingOrderPanel()
	{
		orderTab = new OrderSheetTable();
		setLayout(null);
		
		//¶óº§ »ý¼º
		labelDate = new JLabel("³¯    Â¥");
		labelDate.setBounds(130,50,100,30);
		labelNum = new JLabel("°í°´¹øÈ£");
		labelNum.setBounds(130,100,100,30);
		labelMenu = new JLabel("¸Þ    ´º");
		labelMenu.setBounds(130,150,100,30);
		
		textDate = new JTextField();
		textDate.setBounds(200, 50, 200, 30);
		textNum = new JTextField();
		textNum.setBounds(200, 100, 200, 30);
		comboMenu = new JComboBox(menuList);
		comboMenu.setBounds(200, 150, 200, 30);
		
		btnOrder = new JButton("ÁÖ¹®");
		btnOrder.setBounds(150,300,100,30);
		btnOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startTask(1);
			}
		});
		btnCancel = new JButton("ÁÖ¹®Ãë¼Ò");
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
			orderTab.setManagingMode(mode);
			orderTab.setOrderInfo(textDate.getText(), textNum.getText(), comboMenu.getSelectedIndex());
		
			Thread thread = new Thread(orderTab);
			thread.start();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "ÀÔ·Â È®ÀÎ", JOptionPane.WARNING_MESSAGE);
		}
	}
}
