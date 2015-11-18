package com.kevinmdunne.chess.ui.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class MessagePanel extends JPanel{

	public static final int INFO_MESSAGE = 0;
	public static final int ERROR_MESSAGE = 1;
	public static final int HIGHLIGHT_MESSAGE = 2;
	
	private static final long serialVersionUID = -1191173656674916330L;
	
	private JLabel messageLabel;
	private JLabel playerTurnLabel;
	private JLabel twoPlayerLabel; 
	
	public MessagePanel(){
		super();
		this.setLayout(new BorderLayout(5,5));
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		this.messageLabel = new JLabel("",null,JLabel.CENTER);
		this.messageLabel.setOpaque(true);
		this.messageLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.messageLabel.setPreferredSize(new Dimension(300,18));
		
		this.playerTurnLabel = new JLabel();
		
		this.twoPlayerLabel= new JLabel("",null,JLabel.CENTER); 

		this.add(this.messageLabel, BorderLayout.EAST);
		this.add(this.twoPlayerLabel, BorderLayout.CENTER);
		this.add(this.playerTurnLabel, BorderLayout.WEST);
	}
	
	public void setMessage(String message,int messageType){
		this.messageLabel.setText(message);
		if(messageType == ERROR_MESSAGE){
			this.messageLabel.setBackground(Color.red);
		}else if(messageType == HIGHLIGHT_MESSAGE){
			this.messageLabel.setBackground(Color.green);
		}else{
			this.messageLabel.setBackground(UIManager.getColor("Panel.background"));
		}
	}
	
	public void setMessage(String message){
		this.setMessage(message,INFO_MESSAGE);
	}
	
	public void setPlayerMessage(String message){
		this.playerTurnLabel.setText(message);
	}
	
	public void setTwoPlayerMessage(String message){
		this.twoPlayerLabel.setText(message);
	}
}
