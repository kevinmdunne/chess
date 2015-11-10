package com.kevinmdunne.chess.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.common.eventbus.Subscribe;
import com.kevinmdunne.chess.controller.GameController;
import com.kevinmdunne.chess.controller.events.GameStartedEvent;
import com.kevinmdunne.chess.controller.events.ModelUpdatedEvent;
import com.kevinmdunne.chess.exception.MoveException;
import com.kevinmdunne.chess.ui.control.ControlPanel;
import com.kevinmdunne.chess.ui.message.MessagePanel;


public class GameUI extends JFrame {

	private static final long serialVersionUID = 246790001688212937L;
	
	private BoardUI boardui;
	private MessagePanel messagePanel;
	private ControlPanel controlPanel;
	private DeadPiecesUI deadPiecesUI;
	
	private GameController controller;
	
	public GameUI(){
		this.controller = new GameController();
		this.initUI();
		this.registerListeners();
	}
	
	private void initUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		JPanel boardPanel = new JPanel();
		boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(BorderLayout.CENTER, boardPanel);
		
		this.boardui = new BoardUI(this,boardPanel);
		this.boardui.setBoard(this.controller.getModel());
		
		this.messagePanel = new MessagePanel();
		this.add(this.messagePanel,BorderLayout.SOUTH);
		this.messagePanel.setMessage("Lets get started!!");
		
		this.controlPanel = new ControlPanel(this);
		this.add(this.controlPanel,BorderLayout.NORTH);
		
		this.deadPiecesUI = new DeadPiecesUI(this.controller.getModel());
		this.add(this.deadPiecesUI,BorderLayout.EAST);
		
		this.setTitle("Awesome Chess");
		this.getContentPane().setSize(1200,900);
		this.setSize(1200,900);
	}
	
	private void registerListeners(){
		this.controller.registerListener(this);
	}
	
	public GameController getController(){
		return this.controller;
	}
	
	@Subscribe
	public void handleGameStarted(GameStartedEvent e){
		this.messagePanel.setMessage("Game on!!");
		this.messagePanel.setPlayerMessage("White player's turn");
		this.boardui.refresh();
	}
	
	@Subscribe
	public void handleInvalidMove(MoveException e){
		this.messagePanel.setMessage(e.getMessage(),MessagePanel.ERROR_MESSAGE);
	}
	
	@Subscribe
	public void handleModelUpdated(ModelUpdatedEvent e){
		this.boardui.refresh();
		if(this.controller.isWhitePlayersTurn()){
			this.messagePanel.setPlayerMessage("White player's turn");
		}else{
			this.messagePanel.setPlayerMessage("Black player's turn");
		}
		this.deadPiecesUI.refresh();
		this.messagePanel.setMessage("",MessagePanel.INFO_MESSAGE);
	}
	
	public void setMessage(String message,int messageType){
		this.messagePanel.setMessage(message,messageType);
	}
	
	public static void main(String[] args) {
		System.setProperty("sun.awt.noerasebackground", "true");
		
		GameUI ui = new GameUI();
		ui.setVisible(true);
	}
	
	

}
