package accountManager.view;
import javax.swing.*;

import accountManager.controller.AccountController;
import accountManager.controller.AccountController.Currency;
import accountManager.model.Account;
import accountManager.model.ModelEvent;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class AccountView extends JFrameView {
	public static final String DEPOSIT= "Deposit", WITHDRAW= "Withdraw", DISMISS= "Dismiss";
	private Currency currency;
	private String ActionCommand;
	private JButton deposit, withdraw, dismiss;
	private JTextField textField;
	private Font font;
	private JLabel balanceLabel, textFieldLabel, transactionLabel;
	private JPanel balancePanel, textFieldPanel, buttonPanel, transactionPanel;
	public AccountView(Account model, AccountController controller) { 
		super(model, controller);
		
		currency= ((AccountController)getController()).getCurrency();
		font= new Font(Font.DIALOG, Font.BOLD, 11);
		transactionLabel= new JLabel("No Transaction Has Been Made in This [Operations In " + currency + "]");
		transactionLabel.setFont(font);
		
		transactionPanel= new JPanel();
		transactionPanel.setPreferredSize(new Dimension(637,25));
		transactionPanel.setBackground(Color.cyan);
		transactionPanel.add(transactionLabel);
		
		balanceLabel= new JLabel("balance: " + ((AccountController)getController()).getInitialBalance());
		
		balancePanel= new JPanel();
		balancePanel.setBackground(Color.cyan);
		balancePanel.add(balanceLabel);
		
		textFieldLabel= new JLabel("Enter amount in " + currency + ": ");
		textField= new JTextField(15);
		textField.setToolTipText("0.00");
		textField.setText("0.00");
		textField.addKeyListener(new KeyTypedListener());
		textField.addActionListener (new TempListener());
		
		textFieldPanel= new JPanel();
		textFieldPanel.setBackground(Color.cyan);
		textFieldPanel.add(textFieldLabel);
		textFieldPanel.add(textField);
		
		deposit= new JButton(DEPOSIT);
		deposit.addActionListener(new TempListener());
		withdraw= new JButton(WITHDRAW);
		withdraw.addActionListener(new TempListener());
		dismiss= new JButton(DISMISS);
		dismiss.addActionListener(new TempListener());
		
		buttonPanel= new JPanel();
		buttonPanel.setBackground(Color.cyan);
		buttonPanel.add(deposit);
		buttonPanel.add(withdraw);
		buttonPanel.add(dismiss);
		
		this.setTitle(((Account)getModel()).getID() + " - " + ((Account)getModel()).getName() + " - Initial balance: " + 
		((AccountController)getController()).getInitialBalance() + " - Operations in " + currency);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().add(transactionPanel, BorderLayout.NORTH);
		this.getContentPane().add(balancePanel, BorderLayout.EAST);
		this.getContentPane().add(textFieldPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
		this.setVisible(true);
		
		ActionCommand= null;
	}
	
//  Inner classes for Event Handling 
	private class KeyTypedListener extends KeyAdapter
	{
		public void keyTyped(KeyEvent e) 
		{
			char c= e.getKeyChar();
			if (Character.isLetter(c) && !e.isAltDown())
				e.consume();
		}
	}
	
	private class TempListener implements ActionListener {		
		public void actionPerformed (ActionEvent event) {
			((AccountController)getController()).setAmount(BigDecimal.valueOf(Double.parseDouble(textField.getText())));
			
			if (event.getSource() == deposit || event.getSource() == withdraw || event.getSource() == dismiss)
			{
				ActionCommand= event.getActionCommand();
				((AccountController)getController()).operation(ActionCommand);
			}
		}
	}
	
	public void modelChanged(ModelEvent event) {
		if (currency == Currency.euros)
		{
			BigDecimal temp= ((AccountController)getController()).dollarToEuro(BigDecimal.valueOf(event.getBalance()));
			balanceLabel.setText(event.getKind() + ": " + temp.setScale(2, RoundingMode.HALF_UP) + " " + currency); 
			transactionLabel.setText("A " + ((AccountController)getController()).getAmount().setScale(2, RoundingMode.HALF_UP) + 
					" " + currency + " " + ActionCommand + " And (" + (((AccountController)getController()).getManyTimes()-1) + 
					") Other Transactions Have Been Made in This [Operations In " + currency + "]");
		}
		else if (currency == Currency.yen)
		{
			BigDecimal temp= ((AccountController)getController()).dollarToYen(BigDecimal.valueOf(event.getBalance()));
			balanceLabel.setText(event.getKind() + ": " + temp.setScale(2, RoundingMode.HALF_UP) + " " + currency);
			transactionLabel.setText("A " + ((AccountController)getController()).getAmount().setScale(2, RoundingMode.HALF_UP) + 
					" " + currency + " " + ActionCommand + " And (" + (((AccountController)getController()).getManyTimes()-1) + 
					") Other Transactions Have Been Made in This [Operations In " + currency + "]");
		}
		else
		{
			balanceLabel.setText(event.getKind() + ": " + 
					BigDecimal.valueOf(event.getBalance()).setScale(2, RoundingMode.HALF_UP) + " " + currency);
			transactionLabel.setText("A " + ((AccountController)getController()).getAmount().setScale(2, RoundingMode.HALF_UP) + 
					" " + currency + " " + ActionCommand + " And (" + (((AccountController)getController()).getManyTimes()-1) + 
					") Other Transactions Have Been Made in This [Operations In " + currency + "]");
		}
	}
	
	public void showError(String msg)
	{
		JOptionPane.showMessageDialog(this, msg);
//		JOptionPanel.ShowMessageDialog(null, message);
	}
}