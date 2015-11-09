package com.kevinmdunne.chess.ui.message;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MessagePanel extends JPanel{

	public static final int INFO_MESSAGE = 0;
	public static final int ERROR_MESSAGE = 1;
	
	private static final long serialVersionUID = -1191173656674916330L;
	
	private JLabel messageLabel;
	private JLabel playerTurnLabel;
	
	public MessagePanel(){
		super();
		this.setLayout(new BorderLayout(5,5));
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		this.messageLabel = new JLabel("",null,JLabel.TRAILING);
		this.messageLabel.setOpaque(true);
		this.playerTurnLabel = new JLabel();
		this.add(this.messageLabel, BorderLayout.EAST);
		this.add(this.playerTurnLabel, BorderLayout.WEST);
	}
	
	public void setMessage(String message,int messageType){
		this.messageLabel.setText(message);
		if(messageType == ERROR_MESSAGE){
			this.messageLabel.setBackground(Color.red);
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
}
