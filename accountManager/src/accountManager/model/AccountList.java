package accountManager.model;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * <code>AccountList</code> is a container used for holding multiple {@link Account}s within the Account Manager program.
 * The <code>AccountList</code> has a list containing the <code>Accounts</code> and a counter tracking how many <code>Accounts</code> are in the list.
 * <p>Clients can:
 * <ul>
 * <li>Add new <code>Accounts</code> with <code>addAccount</code>
 * <li>Remove <code>Accounts</code> from the list with <code>removeAcount</code>
 * <li>Get the number of <code>Accounts</code> in the list with <code>getSize</code>
 * <li>Access a specific <code>Account</code> in the list with <code>getAccount</code>
 * <li>Save data in the <code>AccountList</code> to a file with <code>write</code>
 * <li>Load data from a file to <code>AccountList</code> with <code>read</code>
 * </ul>
 * 
 * @author Geek
 * @version %I%, %G%
 */

public class AccountList extends AbstractModel{
	private ArrayList<Account> list;
	private int listLength;
	
	/**
	 * Creates a new empty <code>AccountList</code> object.
	 */
	public AccountList()
	{
		list = new ArrayList<Account>();
		listLength = 0;
	}
	
	/**
	 * Adds an <code>Account</code> to this <code>AccountList</code> object.
	 * 
	 * @param acc The <code>Account</code> object to be added to this <code>AccountList</code>
	 */
	public void addAccount(Account acc)
	{
		list.add(acc);
		listLength = list.size();
		
		//notifyChanged(ModelEvent);
//		ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, acc.getBalance().doubleValue(), AgentStatus.NA);
//		notifyChanged(me);
	}
	
	/**
	 * Removes an <code>Account</code> from this <code>AccountList</code> object.
	 * 
	 * @param index The position in this <code>AccountList</code> of the <code>Account</code> to be removed
	 */
	public void removeAccount(int index)
	{
		if(index >= 0 && index < listLength)
		{
			list.remove(index);
			listLength = list.size();
			
			//notifyChanged(ModelEvent);
//			ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, getAccount(index).getBalance().doubleValue(), AgentStatus.NA);
//			notifyChanged(me);
		}
	}
	
	/**
	 * Get the number of <code>Accounts</code> in this <code>AccountList</code>.
	 * 
	 * @return The number of <code>Accounts</code> in this <code>AccountList</code>
	 */
	public int getSize()
	{
		return listLength;
	}
	
	public ArrayList<Account> getList()
	{
		return list;
	}
	
	/**
	 * Returns an <code>Account</code> in this <code>AccountList</code>.
	 * 
	 * @param index The position in this <code>AccountList</code> of the <code>Account</code> to be accessed
	 * @return The <code>Account</code> located at the index of this <code>AccountList</code>
	 */
	public Account getAccount(int index)
	{
		return list.get(index);
	}
	
	/**
	 * Searches the contents of this <code>AccountList</code> for an <code>Account</code> with a matching ID. 
	 * If multiple <code>Accounts</code> with the same ID exist, returns the last found <code>Account</code>.
	 * 
	 * @param idKey The ID string to search for.
	 * @return The matching <code>Account</code> if it exists in this <code>AccountList</code>.
	 * @throws NoSuchElementException Thrown if no matching <code>Account</code> is found.
	 */
	public Account getAccount(String idKey) throws NoSuchElementException
	{
		//System.out.println("searching for account with ID = " + idKey);
		Account result = null;
		for(int i = 0; i < listLength; ++i)
		{
			//System.out.println("i : " + i + "Account(" + i + ").ID = " + list.get(i).getID());
			if(list.get(i).getID().compareTo(idKey) == 0)
			{
				result = getAccount(i); 
				//System.out.println("Found it!"); 
				i = listLength + 1; 
			}
		}
		if(result != null) { return result;}
		else {throw new NoSuchElementException("No account exists with ID " + idKey);}
	}
	
	/**
	 * Writes the current contents of this <code>AccountList</code> to a file specified by filename.
	 * The number of <code>Account</code> in this <code>AccountList</code> and each member in each <code>Account</code> is written to a new line in the file.
	 * 
	 * @param filename The path, file name, and extension of the file to be written to
	 */
	public void write(String filename)
	{
		//what if account list is empty when write is called?
		//could call an exception, currently just an if statement
		//if list is empty, write does nothing
		if(listLength >  0)
		{
			FileOutputStream fileStream = null;
			BufferedWriter bw = null;
			try
			{
				fileStream = new FileOutputStream(filename);
				bw = new BufferedWriter(new OutputStreamWriter(fileStream));
				
			}
			catch(IOException err)
			{
				System.out.println("Error creating file output stream: " + err.getMessage());
			}
			//write number of accounts to file
			try
			{ 
				bw.write(Integer.toString(listLength)); bw.newLine(); 
			}
			catch(IOException err) {System.out.println(err.getMessage()); }
			
			//for each element in list
			for(int i = 0; i < listLength; ++i)
			{
				try
				{
					//write each member of the current element to filename
					//write curr_ele.getID()
					bw.write(list.get(i).getID());
					bw.newLine();
					//write curr_ele.getName()
					bw.write(list.get(i).getName());
					bw.newLine();
					//write curr_ele.getBalance()
					bw.write(list.get(i).getBalance().toPlainString());
					bw.newLine();
				}
				catch(IOException err)
				{
					System.out.println("Error writing to file " + filename + " : " + err.getMessage());
				}
			}
			
			try{ bw.close(); fileStream.close(); }
			catch(IOException err) 
			{ System.out.println("Error closing file " + filename +" : " + err.getMessage()); }
			
		}
	}
	
	/**
	 * Reads data from a specified file into this <code>AccountList</code> object, overwriting any previous <code>AccountList</code> contents.
	 * Assumes that the file meets the formatting requirements specified in {@link write}
	 * 
	 * @param filename The path, file name, and extension of the file to read data from
	 */
	public void read(String filename)
	{
		File file = new File (filename);
		Scanner scanner = null;
		try 
		{
			scanner = new Scanner(file);
		}
		catch(FileNotFoundException err)
		{
			System.out.println("Error: " + err.getMessage());
		}
		
		//scanner.useDelimiter("\r\n"); //using default delimiter
		//if list isn't empty, clear list first
		if(listLength > 0)
		{
			for(int i = listLength - 1; i < -1; --i)
			{
				this.removeAccount(i);
			}
		}
		
		//read number of accounts
		listLength = scanner.nextInt();
		//System.out.println(listLength);
		scanner.nextLine();
		//reads an empty line w/ unknown blank char for some reason 
		//calling nextLine() once skips the problem line
		//should try to find a better fix later if time permits
		
		//int i = 0;
		while(scanner.hasNext())
		{
			Account temp = new Account();
			
			String nextIn = null;
			//read ID
			nextIn = scanner.nextLine();
			temp.setID(nextIn);
			//System.out.println("[" + i + "] ID: " + nextIn);
			
			//read name
			nextIn = scanner.nextLine();
			temp.setName(nextIn);
			//System.out.println("[" + i + "] Name: " + nextIn);
			
			//read balance
			nextIn = scanner.nextLine();
			temp.deposit(BigDecimal.valueOf(Double.parseDouble(nextIn)));
			//System.out.println("[" + i + "] Balance: " + nextIn);	
			
			//list.add(temp);
			addAccount(temp);
			//++i;
		}
		
		scanner.close();
		
		//notfyChanged(ModelEvent);
//		ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, Double.valueOf(list.size()), AgentStatus.NA);
//		notifyChanged(me);
	}

}
