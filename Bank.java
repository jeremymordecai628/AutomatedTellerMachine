package AutomatedTellerMachine; //import package 
				 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bank {

    private String accountNo;// Store the account number entered by the user
    private String password;// Store  the password
    
    // Call the neccesary   classes
    Data data = new Data() ;
    Deposit deposit = new Deposit(); // This is Mandy's  class
    Withdrawal withdraw = new  Withdrawal(); 



    // Entry method to capture account number
    public void entry() {
        accountNo = JOptionPane.showInputDialog(null,
                "Enter your Account Number to simulate card entry:",
                "Account Entry",
                JOptionPane.PLAIN_MESSAGE);

        password = JOptionPane.showInputDialog(null,
                "Enter PIN",
                "PIN Entry",
                JOptionPane.PLAIN_MESSAGE);
	 if (data.authenticate(accountNo, password)) {
		 JOptionPane.showMessageDialog(null,  "Login successful!");
	 }else{
		  JOptionPane.showMessageDialog(null, "Authetication failed kindly try again.");
		  entry(); // 🔁 Restart the login process
	 }
    }

    // Method to show the main banking GUI
    public void showGUI() {
        JFrame frame = new JFrame("School Banking - Account: " + accountNo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JButton button1 = new JButton("Deposit");
        JButton button2 = new JButton("Withdrawal");
        JButton button3 = new JButton("Check Balance");
	JButton button4 = new JButton("Exit");

        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
	frame.add(button4);

	button1.addActionListener(e -> {
		String Deposit= JOptionPane.showInputDialog(
				null,
				"Enter the amount you want to deposit:",
				"Amount Entry",
				JOptionPane.PLAIN_MESSAGE
				);

		double amount = Double.parseDouble(Deposit);
		// Call Mandy's  class  
		double newBalance = deposit.deposit(accountNo, amount);
		
		JOptionPane.showMessageDialog(
				frame,
				"Deposit for Account " + accountNo + "the  amount deposisted " + Deposit +"your new balance" +newBalance );
	});



        button2.addActionListener(e -> {
		String Amount=JOptionPane.showInputDialog(
				null,
				"Enter the amount you wanna Withdrawal:",
				"Amount Withdrawal",
				JOptionPane.PLAIN_MESSAGE
				);
		double amount = Double.parseDouble(Amount);
		double newBalance = withdraw.withdraw(accountNo, amount);
		JOptionPane.showMessageDialog(frame, "Withdrawal for Account " + accountNo + "Withdrawal amount " +Amount + "your new balance" +newBalance );
        });

        button3.addActionListener(e -> {
		double balance = data.getBalance(accountNo);
		JOptionPane.showMessageDialog(frame, "Check Balance for Account " + accountNo + "account balance" +balance);
        });
	//  Exit button
	button4.addActionListener(e -> {
		int choice = JOptionPane.showConfirmDialog(
				frame,
				"Are you sure you want to exit ?",
				"Happy Customer Week",
				JOptionPane.YES_NO_OPTION
				);
		if (choice == JOptionPane.YES_OPTION) {
			frame.dispose(); // close the current window
			Bank restartApp = new Bank(); // create a fresh instance
			restartApp.entry();           // restart login
			restartApp.showGUI();         // show new GUI
		}
	});


        frame.setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        Bank bankApp = new Bank();
        bankApp.entry();     // Capture account number first
        bankApp.showGUI();   // Then show the main GUI
    }
}

