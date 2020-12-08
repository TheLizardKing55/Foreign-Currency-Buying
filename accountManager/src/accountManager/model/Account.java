package accountManager.model;
import java.math.BigDecimal;
/**
 * Account is a class and the base object within the Account Manager program.
 * Account has members {@link BigDecimal} balance, {@link String} name and int id
 * <p>Clients can:
 * <li>make deposits in Account with deposit
 * <li>withdraw from Account with withdraw
 * <li>set an id for Account with setID
 * <li>set a name for Account with setName
 * <li>get the Account ID with getID
 * <li>get the account balance with getBalance
 * <li>get the account name with getName
 * @author jocar
 */
public class Account extends AbstractModel{
	private BigDecimal balance; 
	private String name; 
	private int id;
	
	/**
	 * Creates a new empty Account object.  
	 */
	public Account()
	{
		balance= new BigDecimal(0.00);
		name= null;
		id= 0;
	}
	
	/**
	 * Creates a new Account object
	 * @param bal the double that will set Account balance
	 * @param name the {@link String} that will set Account name
	 * @param id the integer that will set Account ID
	 */
	public Account(double bal, String name, int id)
	{
		this.name= name;
		this.id= id;
		balance= new BigDecimal(0.00);  
		deposit(BigDecimal.valueOf(bal));
	}
	
	/**
	 * Makes a deposit in Account. It also triggers the 
	 * <p><code>{@code void accountManager.model.AbstractModel.notifyChanged(ModelEvent event)}</code> function
	 * @param amount the {@link BigDecimal} that will be deposited in Account
	 */
	public synchronized void deposit(BigDecimal amount)
	{
		balance= balance.add(amount);
		ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, balance.doubleValue(), AgentStatus.NA);
		notifyChanged(me);
	}
	
	/**
	 * Makes a withdraw from Account. It also triggers the 
	 * <p><code>{@code void accountManager.model.AbstractModel.notifyChanged(ModelEvent event)}</code> function
	 * @param amount the {@link BigDecimal} that will be withdrew from Account
	 * @throws OverdrawException if amount user is trying to withdraw is bigger than Account current balance
	 */
	public synchronized void withdraw (BigDecimal amount) throws OverdrawException
	{
		if (balance.compareTo(amount) < 0)
			throw new OverdrawException("ERROR: The ammount that has been tried to withdraw is bigger than the account\'s current balance.\n");
		
		balance= balance.subtract(amount);
		ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, balance.doubleValue(), AgentStatus.NA);
		notifyChanged(me);
	}
	
	/**
	 * Sets an ID for Account
	 * @param id the {@link String} that will be set as Account ID, which has to be passed as String, 
	 * but it will be casted to integer.
	 */
	public void setID(String id)
	{
		this.id= Integer.parseInt(id);
	}
	
	/**
	 * Sets a name for Account	
	 * @param name the {@link String} that will be set as Account name.
	 */
	public void setName(String name)
	{
		this.name= name;
	}

	/**
	 * Returns the Account ID 
	 * @return Account.id as {@link String}
	 */
	public String getID()
	{
		String strID= String.valueOf(id);
		return strID;
	}
	
	/**
	 * Returns Account name
	 * @return {@link String} Account.name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns Account balance
	 * @return {@link BigDecimal} Account.balance
	 */
	public BigDecimal getBalance()
	{
		return balance;
	}
}
