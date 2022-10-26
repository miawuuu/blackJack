//Mia Wu
//Final project: a gui of the game black jack including betting

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
public class BlackJackWithBetting 
{
	//declare variables
	int dealerPts, playerPts, totalAmount, bet;
	String [] ranks = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
	String [] suits = {"Club","Diamond","Heart","Spade"};
	int [] values = {2,3,4,5,6,7,8,9,10,10,10,10,11};
	Deck myDeck;
	ArrayList<Card> dealerCards;
	ArrayList<Card> playerCards;
	JLabel [] dCardLabels, pCardLabels;
	JFrame frame;
	JPanel mainPanel, dealerPanel, middlePanel, playerPanel, bettingPanel;
	JLabel dealerLabel, playerLabel, betLabel, bankLabel;
	JButton hitButton, standButton, dealButton, newGame;
	JButton bet500, bet100, bet50, bet10, resetBet;
	
	//constructor of the gui
	public BlackJackWithBetting()
	{
		dealerPts = 0;
		playerPts = 0;
		totalAmount = 1000;
		bet = 0;
		myDeck = new Deck(ranks,suits,values);
		dealerCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		
		//frame
		frame = new JFrame("Black Jack (with betting)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//content pane
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		dealerPanel = new JPanel();
		dealerPanel.setLayout(new GridLayout(0,6,5,5));
		dealerPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(0,3,5,5));
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		
		playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(0,6,5,5));
		playerPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		bettingPanel = new JPanel();
		bettingPanel.setLayout(new GridLayout(0,5,5,5));
		bettingPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		//labels
		dealerLabel = new JLabel("Dealer: 0 pts");
		dealerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		dealerLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		playerLabel = new JLabel("Player: 0 pts");
		playerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		playerLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		betLabel = new JLabel("Bet: $"+bet);
		betLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		betLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		bankLabel = new JLabel("Bank: $"+totalAmount);
		bankLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		bankLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		dCardLabels = new JLabel[6];
		pCardLabels = new JLabel[6];
		for(int i = 0; i < dCardLabels.length; i++)
		{
			dCardLabels[i]= new JLabel(new ImageIcon("back_gray.gif"));
			dealerPanel.add(dCardLabels[i]);
		}
		for(int i = 0; i < pCardLabels.length; i++)
		{
			pCardLabels[i]= new JLabel(new ImageIcon("back_gray.gif"));
			playerPanel.add(pCardLabels[i]);
		}
		
		//buttons
		hitButton = new JButton("Hit");
		hitButton.setActionCommand("hit");
		hitButton.addActionListener(new HitStand());
		hitButton.setEnabled(false);
						
		standButton = new JButton("Stand");
		standButton.setActionCommand("stand");
		standButton.addActionListener(new HitStand());
		standButton.setEnabled(false);
						
		dealButton = new JButton("Deal");
		dealButton.setActionCommand("deal");
		dealButton.addActionListener(new HitStand());
		dealButton.setEnabled(false);
		
		bet500 = new JButton("$500");
		bet500.setActionCommand("500");
		bet500.addActionListener(new Betting());
		
		bet100 = new JButton("$100");
		bet100.setActionCommand("100");
		bet100.addActionListener(new Betting());
		
		bet50 = new JButton("$50");
		bet50.setActionCommand("50");
		bet50.addActionListener(new Betting());
		
		bet10 = new JButton("$10");
		bet10.setActionCommand("10");
		bet10.addActionListener(new Betting());
		
		resetBet = new JButton("Reset Bet");
		resetBet.setActionCommand("reset");
		resetBet.addActionListener(new Betting());
		
		bettingPanel.add(bet500);
		bettingPanel.add(bet100);
		bettingPanel.add(bet50);
		bettingPanel.add(bet10);
		bettingPanel.add(resetBet);
		
		middlePanel.add(hitButton);
		middlePanel.add(dealButton);
		middlePanel.add(standButton);
		
		mainPanel.add(dealerPanel);
		mainPanel.add(dealerLabel);
		mainPanel.add(middlePanel);
		mainPanel.add(playerLabel);
		mainPanel.add(playerPanel);
		mainPanel.add(betLabel);
		mainPanel.add(bankLabel);
		mainPanel.add(bettingPanel);
		
		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	//inner class that has actionPerformed method for deal, hit, and stand buttons
	class HitStand implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String eventName = event.getActionCommand();
			
			//deal button: player and dealer each gets 2 cards. 
			if(eventName.equals("deal"))
			{
				hitButton.setEnabled(true);
				standButton.setEnabled(true);
				
				//reset points for both dealer and player
				dealerPts = 0;
				playerPts = 0;
				
				//instantiate a new deck of cards and shuffle it
				myDeck = new Deck(ranks,suits,values);
				dealerCards = new ArrayList<Card>();
				playerCards = new ArrayList<Card>();
				myDeck.shuffle();
				
				//deal card and add it to dealerCards and playerCards arrayLists
				dealerCards.add(myDeck.deal());
				dealerCards.add(myDeck.deal());
				playerCards.add(myDeck.deal());
				playerCards.add(myDeck.deal());
				
				//uses the helper method calculatePts to update the dealer's and player's point
				dealerPts = calculatePts(dealerCards);
				playerPts = calculatePts(playerCards);
				
				//display the players points, but only display the points without the first card for dealer
				playerLabel.setText("Player: "+playerPts+" pts");
				dealerLabel.setText("Dealer: "+(dealerPts-dealerCards.get(0).pointValue())+" pts");
				
				//display the two cards (only reveal one of the dealer's cards)
				for(int j = 0; j<dCardLabels.length; j++)
				{
					dCardLabels[j].setIcon(new ImageIcon("transparent.gif"));
				}
				for(int k = 0; k<pCardLabels.length; k++)
				{
					pCardLabels[k].setIcon(new ImageIcon("transparent.gif"));
				}
				dCardLabels[0].setIcon(new ImageIcon("back_gray.gif"));
				dCardLabels[1].setIcon(getImage(dealerCards.get(1)));
				pCardLabels[0].setIcon(getImage(playerCards.get(0)));
				pCardLabels[1].setIcon(getImage(playerCards.get(1)));
				
				bet500.setEnabled(false);
				bet100.setEnabled(false);
				bet50.setEnabled(false);
				bet10.setEnabled(false);
				resetBet.setEnabled(false);
				dealButton.setEnabled(false);
			}
			
			//hit button: add another card to the player
			else if(eventName.equals("hit"))
			{
				//deal and add a card to the player, and display the new card
				playerCards.add(myDeck.deal());
				pCardLabels[playerCards.size()-1].setIcon(getImage(playerCards.get(playerCards.size()-1)));
				
				//calculate points and update player's points
				playerPts = calculatePts(playerCards);
				playerLabel.setText("Player: "+playerPts+" pts");
				
				//if player's points are greater than 21, then busted
				if(playerPts>21)
				{
					hitButton.setEnabled(false);
					standButton.setEnabled(false);
					
					//reveal both dealer's cards and display dealer's points
					dCardLabels[0].setIcon(getImage(dealerCards.get(0)));
					dealerLabel.setText("Dealer: "+dealerPts+" pts");
					
					//only enable betting buttons that are smaller than remaining amount in the bank
					if(totalAmount>0)
					{
						enableBets();
						resetBet.setEnabled(true);
					}
					
					//pop up gui that shows the result
					FinalMessage myMessage = new FinalMessage(3,bet);
					
					//reset bet
					bet = 0;
					betLabel.setText("Bet: $"+bet);
				}
				
				
			}
			
			//stand button: player does not want any more cards; dealer adds a card until points are greater than 16
			else
			{
				//reveal both of dealer's cards
				dCardLabels[0].setIcon(getImage(dealerCards.get(0)));
				dealerLabel.setText("Dealer: "+dealerPts+" pts");
				
				//keep adding cards until dealer's points are greater than 16
				while (dealerPts<17)
				{
					//deal and add a card for dealer, and display the new card
					dealerCards.add(myDeck.deal());
					dCardLabels[dealerCards.size()-1].setIcon(getImage(dealerCards.get(dealerCards.size()-1)));
					
					//calculate and update dealer's points
					dealerPts = calculatePts(dealerCards);
					dealerLabel.setText("Dealer: "+dealerPts+" pts");
					
					
				}
				
				//player wins if dealer's points are greater than 21
				if(dealerPts>21)
				{
					//pop up gui that shows the result
					FinalMessage message1 = new FinalMessage(1,bet);
					
					//update the amount in the bank
					totalAmount += bet*2;
				}
				
				//player wins if player points are greater than dealer's 
				else if(dealerPts<playerPts)
				{
					//pop up gui that shows the result
					FinalMessage message1 = new FinalMessage(1,bet);
					
					//update the amount in the bank
					totalAmount += bet*2;
				}
				
				//player loses if dealer's points are higher than player's 
				else if(dealerPts>playerPts)
				{
					//pop up gui that shows the result
					FinalMessage message1 = new FinalMessage(2,bet);
				}
				
				
				//push, when player and dealer have the same number of points
				else
				{
					//pop up gui that show the result
					FinalMessage message1 = new FinalMessage(4,bet);
					totalAmount += bet;
				}
				hitButton.setEnabled(false);
				standButton.setEnabled(false);
				
				//only enable betting buttons that are smaller than remaining amount in the bank
				if(totalAmount>0)
				{
					enableBets();
					resetBet.setEnabled(true);
				}
				
				//update bank amount and reset bet
				bankLabel.setText("Bank: $"+totalAmount);
				bet = 0;
				betLabel.setText("Bet: $"+bet);
				
			}
			
		}
	}
	
	//inner class that has actionPerformed for betting buttons
	class Betting implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String eventName = event.getActionCommand();
			
			//add 500 to bet, and update the bet and bank labels
			if(eventName.equals("500"))
			{
				bet+=500;
				betLabel.setText("Bet: $"+bet);
				totalAmount-=500;
				bankLabel.setText("Bank: $"+totalAmount);
				
				//use private method validBets that disables buttons that are greater than the remaining amount in the bank
				disableBets();
				dealButton.setEnabled(true);
			}
			
			//add 100 to bet, and update the bet and bank labels
			else if(eventName.equals("100"))
			{
				bet+=100;
				betLabel.setText("Bet: $"+bet);
				totalAmount-=100;
				bankLabel.setText("Bank: $"+totalAmount);
				disableBets();
				dealButton.setEnabled(true);
				
			}
			
			//add 50 to bet, and update the bet and bank labels
			else if(eventName.equals("50"))
			{
				bet+=50;
				betLabel.setText("Bet: $"+bet);
				totalAmount-=50;
				bankLabel.setText("Bank: $"+totalAmount);
				disableBets();
				dealButton.setEnabled(true);
				
			}
			
			//add 10 to bet, and update the bet and bank labels
			else if(eventName.equals("10"))
			{
				bet+=10;
				betLabel.setText("Bet: $"+bet);
				totalAmount-=10;
				bankLabel.setText("Bank: $"+totalAmount);
				disableBets();
				dealButton.setEnabled(true);
				
			}
			
			//reset bet button: reset bet to 0
			else
			{
				totalAmount += bet;
				bankLabel.setText("Bank: $"+totalAmount);	
				bet=0;
				betLabel.setText("Bet: $"+bet);
				dealButton.setEnabled(false);
				
				//only enable betting buttons that are smaller than remaining amount in the bank
				enableBets();
				
			}
		}
	}
	
	//private method that calculates and returns the points of an ArrayList of cards
	private int calculatePts(ArrayList<Card> cards)
	{
		int total=0;
		int aceCount=0;
		boolean hasAce = false;
		
		//add point value of each cards to total except ace
		for(int i = 0; i<cards.size(); i++)
		{
			Card temp = cards.get(i);
			
			//skip through ace
			if(temp.rank().equals("Ace"))
			{
				hasAce = true;
				aceCount++;
			}
			else
				total += temp.pointValue();
			
		}
		//if ace is in the cards
		if(hasAce)
		{
			for(int i = 0; i < aceCount; i++)
			{
				//if total plus 11 does not bust, then ace is count as 11
				if((total+11) <= 21)
					total = total+11;
				//if total plus 11 busted, then ace is count as 1
				else
					total = total+1; 
			}
			
		}
		return total;
	}
	
	//private method that takes in a card and returns the corresponding image
	private ImageIcon getImage(Card cd)
	{
		ImageIcon icon = null;
		switch (cd.pointValue()) 
		{		
			case 2: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C2.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D2.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H2.gif");
					else
						icon = new ImageIcon("S2.gif");
					break;
			case 3: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C3.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D3.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H3.gif");
					else
						icon = new ImageIcon("S3.gif");
					break;
			
			case 4: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C4.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D4.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H4.gif");
					else
						icon = new ImageIcon("S4.gif");
					break;
			
			case 5: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C5.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D5.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H5.gif");
					else
						icon = new ImageIcon("S5.gif");
					break;
			
			case 6: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C6.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D6.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H6.gif");
					else
						icon = new ImageIcon("S6.gif");
					break;
			
			case 7: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C7.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D7.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H7.gif");
					else
						icon = new ImageIcon("S7.gif");
					break;
			
			case 8 : if(cd.suit().equals("Club"))
						icon = new ImageIcon("C8.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D8.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H8.gif");
					else
						icon = new ImageIcon("S8.gif");
					break;
			
			case 9: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C9.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D9.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H9.gif");
					else
						icon = new ImageIcon("S9.gif");
					break;
			
			case 11: if(cd.suit().equals("Club"))
						icon = new ImageIcon("C1.gif");
					else if (cd.suit().equals("Diamond"))
						icon = new ImageIcon("D1.gif");
					else if (cd.suit().equals("Heart"))
						icon = new ImageIcon("H1.gif");
					else
						icon = new ImageIcon("S1.gif");
					break;	
		}
		
		if(cd.pointValue() == 10)
		{
			if(cd.rank().equals("Jack"))
			{
				if(cd.suit().equals("Club"))
					icon = new ImageIcon("C11.gif");
				else if (cd.suit().equals("Diamond"))
					icon = new ImageIcon("D11.gif");
				else if (cd.suit().equals("Heart"))
					icon = new ImageIcon("H11.gif");
				else
					icon = new ImageIcon("S11.gif");
			}
			else if (cd.rank().equals("Queen"))
			{
				if(cd.suit().equals("Club"))
					icon = new ImageIcon("C12.gif");
				else if (cd.suit().equals("Diamond"))
					icon = new ImageIcon("D12.gif");
				else if (cd.suit().equals("Heart"))
					icon = new ImageIcon("H12.gif");
				else
					icon = new ImageIcon("S12.gif");
			}
			else
			{
				if(cd.suit().equals("Club"))
					icon = new ImageIcon("C13.gif");
				else if (cd.suit().equals("Diamond"))
					icon = new ImageIcon("D13.gif");
				else if (cd.suit().equals("Heart"))
					icon = new ImageIcon("H13.gif");
				else
					icon = new ImageIcon("S13.gif");
			}
		
		
		}
		return icon;
	}
	
	//private method that only enables betting buttons that are smaller than remaining amount in the bank
	private void enableBets()
	{
		if(totalAmount>=500)
		{
			bet500.setEnabled(true);
			bet100.setEnabled(true);
			bet50.setEnabled(true);
			bet10.setEnabled(true);
		}
		else if(totalAmount>=100)
		{
			bet100.setEnabled(true);
			bet50.setEnabled(true);
			bet10.setEnabled(true);
		}
		else if(totalAmount>=50)
		{
			bet50.setEnabled(true);
			bet10.setEnabled(true);
		}
		else 
		{
			bet10.setEnabled(true);
		}
	}

	//private method that disables the betting buttons that are greater than remaining amount in the bank
	private void disableBets()
	{
		if(totalAmount<10)
		{
			bet500.setEnabled(false);
			bet100.setEnabled(false);
			bet50.setEnabled(false);
			bet10.setEnabled(false);
			
		}
		else if(totalAmount<50)
		{
			bet500.setEnabled(false);
			bet100.setEnabled(false);
			bet50.setEnabled(false);
		}
		else if(totalAmount<100)
		{
			bet500.setEnabled(false);
			bet100.setEnabled(false);
			
		}
		else if(totalAmount<500)
		{
			bet500.setEnabled(false);
		}
	}
	
	public static void runGUI()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		BlackJackWithBetting gui = new BlackJackWithBetting();
	}
	public static void main(String[]args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();

			}
		});
	}

}
