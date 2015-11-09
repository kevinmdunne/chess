package com.kevinmdunne.chess.ui.control;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.kevinmdunne.chess.ui.GameUI;

public class ControlPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -657005315924516670L;
	
	private JButton startButton;
	private GameUI parent;
	
	public ControlPanel(GameUI parent){
		super();
		
		this.parent = parent;
		this.startButton = new JButton("Start Game");
		this.startButton.setName("startButton");
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(this.startButton);
		this.startButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.startButton)){
			this.parent.getController().startGame();
		}
		
	}
}
