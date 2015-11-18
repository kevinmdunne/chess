package com.kevinmdunne.chess.ui.control;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kevinmdunne.chess.ui.GameUI;
import com.kevinmdunne.chess.ui.remote.JoinRemoteGameDialog;
import com.kevinmdunne.chess.ui.remote.StartRemoteGameDialog;

public class ControlPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -657005315924516670L;
	
	private JButton startButton;
	private JButton startRemoteButton;
	private JButton joinRemoteButton;
	private GameUI parent;
	
	public ControlPanel(GameUI parent){
		super();
		
		this.parent = parent;
		
		this.startButton = new JButton("Start Game");
		this.startButton.setName("startButton");
		
		this.startRemoteButton = new JButton("Start Remote Game");
		this.startRemoteButton.setName("startRemoteButton");
		
		this.joinRemoteButton = new JButton("Join Remote Game");
		this.joinRemoteButton.setName("joinRemoteButton");
		
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		this.add(this.startButton);
		this.add(this.startRemoteButton);
		this.add(this.joinRemoteButton);
		
		this.startButton.addActionListener(this);
		this.startRemoteButton.addActionListener(this);
		this.joinRemoteButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.startButton)){
			this.parent.getController().startGame();
		}else if(e.getSource().equals(this.startRemoteButton)){
			this.startRemoteGame();
		}else if(e.getSource().equals(this.joinRemoteButton)){
			this.joinRemoteGame();
		}
	}
	
	public void disableButtons(){
		this.startButton.setEnabled(false);
		this.startRemoteButton.setEnabled(false);
		this.joinRemoteButton.setEnabled(false);
	}
	
	public void enableButtons(){
		this.startButton.setEnabled(true);
		this.startRemoteButton.setEnabled(true);
		this.joinRemoteButton.setEnabled(true);
	}
	
	private void startRemoteGame(){
		StartRemoteGameDialog dialog = new StartRemoteGameDialog(this.parent,this.parent.getController());
		dialog.setVisible(true);
	}
	
	private void joinRemoteGame(){
		JoinRemoteGameDialog dialog = new JoinRemoteGameDialog(this.parent,this.parent.getController());
		dialog.setVisible(true);
	}
}
