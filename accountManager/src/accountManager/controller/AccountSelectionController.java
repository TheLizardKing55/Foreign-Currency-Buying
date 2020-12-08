package accountManager.controller;
import accountManager.controller.AccountController.Currency;
import accountManager.model.Account;
import accountManager.model.AccountList;
import accountManager.view.AccountSelectionView;
public class AccountSelectionController extends AbstractController {
	public static final String FILE_NAME= "List of Accounts";
	private Currency currency;
	private String strBalance;
	private Account account;
	
	public AccountSelectionController()
	{
		setModel(new AccountList());
		
		((AccountList)getModel()).read(FILE_NAME);
		
		strBalance= "";
		currency= Currency.dollars;
		account= ((AccountList)getModel()).getAccount(0);
		
		setView(new AccountSelectionView((AccountList)getModel(), this));
//		((JFrameView)getView()).setVisible(true); 
	}
	
	public String[] getArray()
	{
		
		int size= ((AccountList)getModel()).getSize();
		String[] array= new String[size+1];
		array[0]= "Make A Selection...";
		for(int i=0; i < size; i++)
		{
			array[i+1]= ((AccountList)getModel()).getAccount(i).getID() + " - " + ((AccountList)getModel()).getAccount(i).getName();
		}
		
		return array;
	}
	
	public void operation(String option)
	{
		if (option == AccountSelectionView.SAVE)
			((AccountList)getModel()).write(FILE_NAME);
		else if (option == AccountSelectionView.EXIT)
		{
			((AccountList)getModel()).write(FILE_NAME);
			
			System.exit(0);
		}
		else if(option == AccountSelectionView.DOLLAR)
		{
			currency= Currency.dollars;
			new AccountController(account, currency);
		}
		else if (option == AccountSelectionView.EURO)
		{
			currency= Currency.euros;
			new AccountController(account, currency);
		}
		else if (option == AccountSelectionView.YEN)
		{   
			currency= Currency.yen;
			new AccountController(account, currency);
		}
	}
	
	public void setSelectedAccount(Account selected)
	{
		this.account= selected;
	}
	
	public void setStrBalance(String bal)
	{
		this.strBalance= bal;
	}
	
	public String getStrBalance()
	{
		return strBalance;
	}
}