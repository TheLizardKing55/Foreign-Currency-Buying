package accountManager.test;
import java.math.BigDecimal;

import accountManager.model.*;

public class AccountTest {
	public static void main(String[] args) {
		//Create a new account
		Account anAccount= new Account();
		
		anAccount.setID("2012");
		anAccount.setName("John Lennon");
		System.out.println("An account has been created with ID: " + anAccount.getID() + " and name: " + anAccount.getName());
		
		//Make a deposit to the account 
		System.out.println("Making a deposit of $40.73 to the account.");
		System.out.println();
		anAccount.deposit(BigDecimal.valueOf(40.73));
		
		//Check out account info
		System.out.println("Account ID: " + anAccount.getID() + " Name: " + anAccount.getName());
		System.out.println("Has balance: $" + anAccount.getBalance());
		System.out.println();
		
		//Make a withdraw from account
		System.out.println("Making a withdraw of $30.12");
		try
		{
			anAccount.withdraw(BigDecimal.valueOf(30.12));
		}
		catch(OverdrawException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		//Check out account info
		System.out.println("Account ID: " + anAccount.getID() + " Name: " + anAccount.getName());
		System.out.println("Has balance: $" + anAccount.getBalance());
		System.out.println();
		
		//Make a withdraw that exceed account balance to test the OverdrawException
		System.out.println("Making a withdraw of $11.42");
		try
		{
			anAccount.withdraw(BigDecimal.valueOf(11.42));
		}
		catch(OverdrawException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		//Check out account info
		System.out.println("Account ID: " + anAccount.getID() + " Name: " + anAccount.getName());
		System.out.println("Has balance: $" + anAccount.getBalance());
	}

}
