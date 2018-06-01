import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
		int num = 0;
		Date orderDate;
		try
		{
			for (int i = 0; i < customerTab.getSize(); i++)
			{
				num = 0;
				for (int j = 0; j < orderTab.getSize(); j++)
				{
					orderDate = format.parse(orderTab.getOrder(j).getDate());
					int compareStartDate = startDate.compareTo(orderDate);
					int compareEndDate = endDate.compareTo(orderDate);

					if (customerTab.getCustomer(i).getCustomNum().equals(orderTab.getOrder(j).getCustomNum())
							&& (compareStartDate == 0 | compareStartDate < 0)
							&& (compareEndDate == 0 | compareEndDate > 0))
					{
						num++;
					}
				}

				totalNum += num / 3;
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		couponNum = totalNum;
	}

	public void setDates()
	{
		try
		{
			startDate = format.parse(panel.getTextDateStart().getText());
			endDate = format.parse(panel.getTextDateEnd().getText());
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					
					if (orderTab.getOrder(j).getMenu() == i
							&& (compareStartDate == 0 | compareStartDate < 0)
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

		setDates();
		setSalesInfo();
		countCouponNum();

		System.out.println(startDate + " " + endDate);
		String output = "";
		output += "¸Þ´º                                  °³¼ö                  ¸ÅÃâ±Ý¾×\n";
		output += "===========================================\n";
		output += "±è¹ä                                     " + salesTotalNum[0] + "                      "
				+ salesTotalMoney[0] + "\n";
		output += "¶±ººÀÌ                                 " + salesTotalNum[1] + "                      "
				+ salesTotalMoney[1] + "\n";
		output += "¼ø´ë                                     " + salesTotalNum[2] + "                      "
				+ salesTotalMoney[2] + "\n";
		output += "¾î¹¬                                     " + salesTotalNum[3] + "                      "
				+ salesTotalMoney[3] + "\n";
		output += "Æ¢±è                                     " + salesTotalNum[4] + "                      "
				+ salesTotalMoney[4] + "\n";
		output += "ÄíÆù                                     " + couponNum + "\n";
		output += "===========================================\n";
		output += "¸ÅÃâÇÕ°è                                                    ";

		panel.getTextInfoView().append(output);
	}
}
