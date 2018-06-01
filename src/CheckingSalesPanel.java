import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CheckingSalesPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SalesTable salesTab;
	
	private JLabel labelDate;
	private JLabel labelWave;
	private JLabel labelTo;
	
	private JTextField textDate1;
	private JTextField textDate2;
	private JTextArea textInfoView;
	
	private JButton btnCheck;
	private JButton btnCancel;
	
	public CheckingSalesPanel(SalesTable salesTab)
	{
		this.salesTab = salesTab;
		setLayout(null);
		
		labelDate = new JLabel("날짜");
		labelDate.setBounds(130,50,100,30);
		labelWave = new JLabel("~");
		labelWave.setBounds(350,50,100,30);
		labelTo = new JLabel("까지");
		labelTo.setBounds(530,50,100,30);
		
		textDate1 = new JTextField();
		textDate1.setBounds(180,50,130,30);
		textDate2 = new JTextField();
		textDate2.setBounds(380,50,130,30);
		textInfoView = new JTextArea();
		textInfoView.setBounds(130,100,450,220);
		
		btnCheck = new JButton("조회");
		btnCheck.setBounds(130,350,200,30);
		btnCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				textInfoView.setText("");
				salesTab.checkSales();
			}
		});
		btnCancel = new JButton("취소");
		btnCancel.setBounds(380,350,200,30);
		
		add(labelDate);
		add(labelWave);
		add(labelTo);
		add(textDate1);
		add(textDate2);
		add(textInfoView);
		add(btnCheck);
		add(btnCancel);
	}
	
	public JTextArea getTextInfoView()
	{
		return textInfoView;
	}
	
	public JTextField getTextDateStart()
	{
		return textDate1;
	}
	
	public JTextField getTextDateEnd()
	{
		return textDate2;
	}
}
