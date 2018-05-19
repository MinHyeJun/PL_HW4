import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ManagingOrderPanel extends JPanel implements Runnable
{
	private OrderSheetTable orderTab;
	
	private JLabel labelDate;
	private JLabel labelNum;
	private JLabel labelMenu;
	
	private JTextField textDate;
	private JTextField textNum;
	private JComboBox comboMenu;
	private String[] menuList = {"���", "������", "����", "�", "Ƣ��"};
	
	private JButton btnOrder;
	private JButton btnCancel;
	
	public ManagingOrderPanel()
	{
		orderTab = new OrderSheetTable();
		setLayout(null);
		
		//�� ����
		labelDate = new JLabel("��    ¥");
		labelDate.setBounds(130,50,100,30);
		labelNum = new JLabel("����ȣ");
		labelNum.setBounds(130,100,100,30);
		labelMenu = new JLabel("��    ��");
		labelMenu.setBounds(130,150,100,30);
		
		textDate = new JTextField();
		textDate.setBounds(200, 50, 200, 30);
		textNum = new JTextField();
		textNum.setBounds(200, 100, 200, 30);
		comboMenu = new JComboBox(menuList);
		comboMenu.setBounds(200, 150, 200, 30);
		
		btnOrder = new JButton("�ֹ�");
		btnOrder.setBounds(150,300,100,30);
		btnOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(textDate.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "no �ֹ���");
					return;
				}
				else if(textNum.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "no ����ȣ");
					return;
				}
				
			}
		});
		btnCancel = new JButton("�ֹ����");
		btnCancel.setBounds(350,300,100,30);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(textDate.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "no �ֹ���");
					return;
				}
				else if(textNum.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "no ����ȣ");
					return;
				}
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

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}
}
