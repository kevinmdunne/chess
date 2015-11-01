package com.kevinmdunne.chess.ui.message;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel extends JPanel{

	private static final long serialVersionUID = -1191173656674916330L;
	
	private JLabel label;
	
	public MessagePanel(){
		super();
		this.setLayout(new BorderLayout());
		this.label = new JLabel();
		this.add(this.label, BorderLayout.CENTER);
	}
	
	public void setMessage(String message){
		this.label.setText(message);
	}
}
