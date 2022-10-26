//Mia Wu
//This is a pop up gui that shows the result and how much money the player gained or lost.

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FinalMessage 
{
	//declare variables
	JFrame frame;
	JPanel contentPane;
	JLabel label, amountWinLose;
	int bet;
	
	public FinalMessage(int num, int amount)
	{
		bet = amount;
		frame = new JFrame(" ");
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		label = new JLabel("       ");
		amountWinLose = new JLabel("       ");
		contentPane.add(label);
		contentPane.add(amountWinLose);
		
		//display the corresponding text and how much the player win or lose
		if(num==1)
		{
			label.setText("You Won!");
			amountWinLose.setText("+ $"+(bet*2));
		}
		else if (num ==2)
		{
			label.setText("You Lost.");
			amountWinLose.setText("- $"+bet);
		}
		else if(num == 3)
		{
			label.setText("Busted");
			amountWinLose.setText("- $"+bet);
		}
		else
		{
			label.setText("Push");
			amountWinLose.setText("+ $"+bet);
		}
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setVisible(true);
	}
	
}
