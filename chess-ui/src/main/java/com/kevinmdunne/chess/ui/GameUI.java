package com.kevinmdunne.chess.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.kevinmdunne.chess.controller.GameController;
import com.kevinmdunne.chess.model.Board;
import com.kevinmdunne.chess.ui.control.ControlPanel;
import com.kevinmdunne.chess.ui.message.MessagePanel;


public class GameUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 246790001688212937L;
	
	private JPanel boardPanel;
	private MessagePanel messagePanel;
	private ControlPanel controlPanel;
	
	private GameController controller;
	
	public GameUI(){
		this.controller = new GameController();
		this.initUI();
	}
	
	private void initUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.boardPanel = new JPanel();
		this.add(BorderLayout.CENTER, this.boardPanel);
		
		Board board = new Board();
		BoardUI boardui = new BoardUI(boardPanel);
		boardui.setBoard(board);
		
		this.messagePanel = new MessagePanel();
		this.add(this.messagePanel,BorderLayout.SOUTH);
		this.messagePanel.setMessage("Lets get started!!");
		
		this.controlPanel = new ControlPanel();
		this.add(this.controlPanel,BorderLayout.NORTH);
		
		this.getContentPane().setSize(1200,900);
		this.setSize(1200,900);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source instanceof JButton){
			if(((JButton)source).getName().equals("startButton")){
				this.controller.startGame();
			}
		}
	}
	
	public static void main(String[] args) {
		GameUI ui = new GameUI();
		ui.setVisible(true);
	}

}
