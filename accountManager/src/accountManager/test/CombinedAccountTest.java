package accountManager.test;

import accountManager.model.Account;
import accountManager.model.AccountList;

public class CombinedAccountTest {
	public static void main(String[] args) {
		//Creating some accounts
		Account account1= new Account(40.73, "John Lennon", 101);
		Account account2= new Account(30.12, "Paul MacCarney", 102);
		Account account3= new Account(35.42, "George Harrison", 103);
		Account account4= new Account(42.33, "Ringo Star", 104);
		
		//Create an accountList
		AccountList aList= new AccountList();
		
		//Adding accounts to list
		aList.addAccount(account1);
		aList.addAccount(account2);
		aList.addAccount(account3);
		aList.addAccount(account4);
		System.out.println("Some accounts has been added to the list");
		System.out.println();
		
		//Writes account in file
		System.out.println("Writing account's info in a file..");
		String fileName= "List of Accounts";
		aList.write(fileName);
		System.out.println ("Output file has been created: " + fileName);
		System.out.println();
		
		//Create another accountList
		AccountList anotherList= new AccountList();
		
		//Read file to accountList
		System.out.println("Reading file to another accountList...");
		System.out.println();
		anotherList.read(fileName);
		
		//Checking out accountList
		System.out.println("Printing accounts...");
		for(int i=0; i < anotherList.getSize(); i++)
		{
			System.out.println((anotherList.getAccount(i)).getID() + " " + 
		(anotherList.getAccount(i)).getName() + " " + (anotherList.getAccount(i)).getBalance());
		
		}
		
	}

}
