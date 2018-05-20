import javax.swing.*;

public class ManagingProgram extends JFrame
{
	final static int x1 = 500;
	final static int y1 = 200;
	final static int x2 = 700;
	final static int y2 = 500;
	
	private static final long serialVersionUID = -711163588504124217L;

	
	public ManagingOrderPanel orderPanel = null;
	public ManagingCustomerPanel customPanel = null;
	
	public ManagingProgram() {
		
		// ������ â ����
		super("Managing Customer Program");
		setBounds(x1, y1, x2, y2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		orderPanel = new ManagingOrderPanel();
		customPanel = new ManagingCustomerPanel();
		
		JTabbedPane jTab = new JTabbedPane();
		add(jTab);
		jTab.addTab("�ֹ�����", orderPanel);
		jTab.addTab("������", customPanel);

		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new ManagingProgram();
		
	}
}
