package accountManager.test;
import accountManager.model.*;

public class AccountListTest {
	public static void main(String[] args) {
		//Creating some accounts
		Account account1= new Account(40.73, "John Lennon", 001);
		Account account2= new Account(30.12, "Paul MacCarney", 002);
		Account account3= new Account(35.42, "George Harrison", 003);
		Account account4= new Account(42.33, "Ringo Star", 004);
		
		//Create an accountList
		AccountList aList= new AccountList();
		
		//Adding accounts to list
		aList.addAccount(account1);
		aList.addAccount(account2);
		System.out.println("Some accounts has been added to the list");
		System.out.println();
		
		//Checking out accountList
		System.out.println("Printing accounts...");
		for(int i=0; i < aList.getSize(); i++)
		{
			System.out.println("Account ID: " + (aList.getAccount(i)).getID() + " - Name: " + 
		(aList.getAccount(i)).getName() + ", - balance: $" + (aList.getAccount(i)).getBalance());
		}
		System.out.println();
		
		//Adding two more accounts to the list
		System.out.println("Adding two more account to the list...");
		System.out.println();
		aList.addAccount(account3);
		aList.addAccount(account4);
		
		//Checking out accountList
		System.out.println("Printing accounts...");
		for(int i=0; i < aList.getSize(); i++)
		{
			System.out.println("Account ID: " + (aList.getAccount(i)).getID() + " - Name: " + 
		(aList.getAccount(i)).getName() + ", - balance: $" + (aList.getAccount(i)).getBalance());
		}
		System.out.println();
		
		//Remove the 4th account
		System.out.println("Removing the 4th account in list...");
		System.out.println();
		aList.removeAccount(3);
		
		//Checking out accountList
		System.out.println("Printing accounts...");
		for(int i=0; i < aList.getSize(); i++)
		{
			System.out.println("Account ID: " + (aList.getAccount(i)).getID() + " Name: " + 
		(aList.getAccount(i)).getName() + " balance: $" + (aList.getAccount(i)).getBalance());
		}
	}
}
