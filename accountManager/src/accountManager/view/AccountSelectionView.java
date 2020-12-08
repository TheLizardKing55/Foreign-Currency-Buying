package accountManager.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import accountManager.controller.AccountSelectionController;
import accountManager.model.Account;
import accountManager.model.AccountList;
import accountManager.model.ModelEvent;

public class AccountSelectionView extends JFrameView {
	public static final String SAVE = "save", EXIT = "exit", DOLLAR= "Edit account in Dollars",
								EURO= "Edit account in Euros", YEN= "Edit account in Yen";
	private JComboBox accountCombo;
	private JButton save, exit, dollars, euros, yen;
	private JPanel ComboBoxPanel, endingPanel, editPanel;
	private Account current;
	
	public AccountSelectionView(AccountList model, AccountSelectionController controller) { 
		super(model, controller);
		
		dollars= new JButton(DOLLAR);
		dollars.addActionListener(new ButtonListener());
		euros= new JButton(EURO);
		euros.addActionListener(new ButtonListener());
		yen= new JButton(YEN);
		yen.addActionListener(new ButtonListener());
		
		editPanel= new JPanel();
		editPanel.setPreferredSize(new Dimension(666, 37));
		editPanel.setBackground(Color.cyan);
		editPanel.add(dollars);
		editPanel.add(euros);
		editPanel.add(yen);
		
		save= new JButton(SAVE);
		save.addActionListener(new ButtonListener());
		exit= new JButton(EXIT);
		exit.addActionListener(new ButtonListener());
		
		endingPanel= new JPanel();
		endingPanel.setBackground(Color.cyan);
		endingPanel.add(save);
		endingPanel.add(exit);
		
		String[] accountsArr= ((AccountSelectionController)getController()).getArray();
		accountCombo= new JComboBox(accountsArr);
		accountCombo.addActionListener (new ComboListener());
		
		ComboBoxPanel = new JPanel();
		ComboBoxPanel.setBackground(Color.cyan);
		ComboBoxPanel.setLayout(new GridLayout(4, 4, 5, 5));
		ComboBoxPanel.add(accountCombo);

		this.setTitle("Account Manager");
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(ComboBoxPanel, BorderLayout.NORTH);
		this.getContentPane().add(editPanel, BorderLayout.CENTER);
		this.getContentPane().add(endingPanel, BorderLayout.SOUTH);
		pack();
		this.setVisible(true);
		
		current= null;
	}
	
	// Inner classes for Event Handling 
	private class ComboListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			current = ((AccountList)getModel()).getAccount(accountCombo.getSelectedIndex()-1);
			((AccountSelectionController)getController()).setSelectedAccount(current);
		}
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			((AccountSelectionController)getController()).operation(event.getActionCommand());
		}
	}
	
	public void modelChanged(ModelEvent event) {
		String msg = String.valueOf(event.getBalance());
		System.out.println("In SelectionView balance is: " + msg);
		((AccountSelectionController)getController()).setStrBalance(msg);
	}
	
	public static void main(String [] args) { new AccountSelectionController();}
}
