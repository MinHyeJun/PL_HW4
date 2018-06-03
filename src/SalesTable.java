import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class SalesTable
{
	private CustomerTable customerTab;
	private OrderSheetTable orderTab;

	private CheckingSalesPanel panel;

	SimpleDateFormat format;
	private Date startDate;
	private Date endDate;
	private int[] salesTotalNum = { 0, 0, 0, 0, 0 };
	private int[] salesTotalMoney = { 0, 0, 0, 0, 0 };
	private int[] priceOfMenu = { 1500, 3000, 500, 3000, 500 };

	private int couponNum;

	public SalesTable(CustomerTable customerTab, OrderSheetTable orderTab)
	{
		this.customerTab = customerTab;
		this.orderTab = orderTab;
		format = new SimpleDateFormat("yyyy/MM/dd");
		couponNum = 0;
	}

	public void setPanel(CheckingSalesPanel panel)
	{
		this.panel = panel;
	}

	public int getCouponNum()
	{
		return couponNum;
	}

	public void countCouponNum()
	{
		int totalNum = 0;
		int preNum = 0;
		int orderNum = 0;
		Date orderDate;
		try
		{
			for (int i = 0; i < customerTab.getSize(); i++)
			{
				orderNum = 0;
				totalNum = 0;
				for (int j = 0; j < orderTab.getSize(); j++)
				{
					orderDate = format.parse(orderTab.getOrder(j).getDate());
					int compareStartDate = startDate.compareTo(orderDate);
					int compareEndDate = endDate.compareTo(orderDate);

					if (customerTab.getCustomer(i).getCustomNum().equals(orderTab.getOrder(j).getCustomNum())
							&& (compareEndDate == 0 | compareEndDate > 0))
					{
						orderNum++;
					}

					if (customerTab.getCustomer(i).getCustomNum().equals(orderTab.getOrder(j).getCustomNum())
							&& compareStartDate > 0)
					{
						preNum++;
					}
				}

				totalNum += (orderNum / 3) - (preNum / 3);
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		couponNum = totalNum;
	}

	public void setDates() throws ParseException, Exception
	{
		String strStartDate = panel.getTextDateStart().getText();
		String strEndDate = panel.getTextDateEnd().getText();
		
		if(strStartDate.equals("") | strEndDate.equals(""))
			throw new WrongInputDataException("날짜를 모두 기재해주십시오.");
		
		startDate = format.parse(strStartDate);
		endDate = format.parse(strEndDate);

		if (startDate.compareTo(endDate) > 0)
			throw new Exception("입력된 날짜로 조회할 수 없습니다.");
	}
	
	public int sumTotalMoney()
	{
		int totalMoney = 0;
		for(int i = 0; i < salesTotalMoney.length; i++)
			totalMoney += salesTotalMoney[i];
		
		return totalMoney;
	}

	public void setSalesInfo()
	{
		int num;
		Date orderDate;
		for (int i = 0; i < 5; i++)
		{
			num = 0;
			try
			{
				for (int j = 0; j < orderTab.getSize(); j++)
				{
					orderDate = format.parse(orderTab.getOrder(j).getDate());
					int compareStartDate = startDate.compareTo(orderDate);
					int compareEndDate = endDate.compareTo(orderDate);

					if (orderTab.getOrder(j).getMenu() == i && (compareStartDate == 0 | compareStartDate < 0)
							&& (compareEndDate == 0 | compareEndDate > 0))
					{
						num++;
					}
				}
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}

			salesTotalNum[i] = num;
			salesTotalMoney[i] = num * priceOfMenu[i];
		}
	}

	public void checkSales()
	{
		try
		{
		setDates();
		setSalesInfo();
		countCouponNum();

		System.out.println(startDate + " " + endDate);
		String output = "";
		output += "메뉴                                  개수                  매출금액\n";
		output += "===========================================\n";
		output += "김밥                                     " + salesTotalNum[0] + "                      "
				+ salesTotalMoney[0] + "\n";
		output += "떡볶이                                 " + salesTotalNum[1] + "                      "
				+ salesTotalMoney[1] + "\n";
		output += "순대                                     " + salesTotalNum[2] + "                      "
				+ salesTotalMoney[2] + "\n";
		output += "어묵                                     " + salesTotalNum[3] + "                      "
				+ salesTotalMoney[3] + "\n";
		output += "튀김                                     " + salesTotalNum[4] + "                      "
				+ salesTotalMoney[4] + "\n";
		output += "쿠폰                                     " + couponNum + "\n";
		output += "===========================================\n";
		output += "매출합계                                                      " + sumTotalMoney();

		panel.getTextInfoView().append(output);
		}
		catch(ParseException e)
		{
			JOptionPane.showMessageDialog(null, "잘못된 형식의 날짜가 입력되었습니다.", "입력 확인", JOptionPane.WARNING_MESSAGE);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "입력 확인", JOptionPane.WARNING_MESSAGE);
		}
	}
}
