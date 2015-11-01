package com.kevinmdunne.chess.ui.control;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{

	private JButton startButton;
	
	public ControlPanel(){
		super();
		
		this.startButton = new JButton("Start Game");
		this.startButton.setName("startButton");
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(this.startButton);
	}
	
	public void setActionListener(ActionListener actionlistenter){
		ActionListener[] listeners = this.startButton.getActionListeners();
		for(ActionListener listener : listeners){
			this.startButton.removeActionListener(listener);
		}
		this.startButton.addActionListener(actionlistenter);
	}
}
