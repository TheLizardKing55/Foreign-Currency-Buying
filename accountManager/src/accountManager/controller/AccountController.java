package accountManager.controller;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import accountManager.model.Account;
import accountManager.model.OverdrawException;
import accountManager.view.AccountView;
import accountManager.view.JFrameView;
public class AccountController extends AbstractController {
	public enum Currency{dollars, euros, yen}
	public static final BigDecimal DOLLAR_EURO_REASON= new BigDecimal(0.79);
	public static final BigDecimal DOLLAR_YEN_REASON= new BigDecimal (94.1);
	private Currency currency;
	private String initialBalance;
	private BigDecimal amount;
	private int manyTimes;
	
	public AccountController() {
		setModel(new Account());
		
		manyTimes= 0;
		initialBalance= "";
		currency= Currency.dollars;
		amount= new BigDecimal(0.00);
		
		setView(new AccountView((Account)getModel(), this));
	}
	
	public AccountController(Account account, Currency currency)
	{
		setModel(account);
		
		manyTimes= 0;
		this.currency= currency;
		amount= new BigDecimal(0.00);
		
		if (currency == Currency.dollars)
			this.initialBalance= account.getBalance().setScale(2, RoundingMode.HALF_UP).toPlainString() + " " + currency;
		
		else if (currency == Currency.euros)
			this.initialBalance= dollarToEuro(account.getBalance()).setScale(2, RoundingMode.HALF_UP).toPlainString() + " " + currency;
		else	
			this.initialBalance= dollarToYen(account.getBalance()).setScale(2, RoundingMode.HALF_UP).toPlainString() + " " + currency;
		
		setView(new AccountView((Account)getModel(), this));
	}
	
	public void operation(String option)
	{
		manyTimes++;
		
		if (option == AccountView.DEPOSIT)
		{
			if (currency == Currency.euros)
			{
				BigDecimal temp= euroToDollar(amount);
				((Account)getModel()).deposit(temp);
			}
			else if(currency == Currency.yen)
			{
				BigDecimal temp= yenToDollar(amount);
				((Account)getModel()).deposit(temp);
			}
			else
				((Account)getModel()).deposit(amount);
		}
		
		else if(option == AccountView.WITHDRAW)
		{
			if (currency == Currency.euros)
			{
				BigDecimal temp= euroToDollar(amount);
				try {((Account)getModel()).withdraw(temp);}
				catch(OverdrawException ex) {
					System.out.println(ex.getMessage());
					((AccountView)getView()).showError(ex.getMessage());
				}
				
			}
			else if (currency == Currency.yen)
			{
				BigDecimal temp= yenToDollar(amount);
				try {((Account)getModel()).withdraw(temp);}
				catch(OverdrawException ex) {
					System.out.println(ex.getMessage());
					((AccountView)getView()).showError(ex.getMessage());
				}
			}
			else
			{	
				try {((Account)getModel()).withdraw(amount);}
				catch(OverdrawException ex) {
					System.out.println(ex.getMessage());
					((AccountView)getView()).showError(ex.getMessage());
				}
			}
		
		}
		
		else
			((JFrameView)getView()).dispose();
	}
	
	public BigDecimal euroToDollar(BigDecimal euro)
	{			
		BigDecimal dollar= euro.divide(DOLLAR_EURO_REASON, 9, RoundingMode.HALF_UP);
		return dollar;
	}
	
	public BigDecimal dollarToEuro(BigDecimal dollar)
	{
		BigDecimal euro= dollar.multiply(DOLLAR_EURO_REASON, MathContext.DECIMAL32);
		return euro;
	}
	
	public BigDecimal yenToDollar(BigDecimal yen)
	{
		BigDecimal dollar= yen.divide(DOLLAR_YEN_REASON, 9, RoundingMode.HALF_UP);
		return dollar;
	}
	
	public BigDecimal dollarToYen(BigDecimal dollar)
	{
		BigDecimal yen= dollar.multiply(DOLLAR_YEN_REASON, MathContext.DECIMAL32);
		return yen;
	}
	
	public void setAmount(BigDecimal amount)
	{
		this.amount= amount;
	}
	
	public BigDecimal getAmount()
	{
		return amount;
	}
	
	public String getInitialBalance()
	{
		return initialBalance;
	}
	
	public Currency getCurrency()
	{
		return currency;
	}
	
	public int getManyTimes()
	{
		return manyTimes;
	}
}
