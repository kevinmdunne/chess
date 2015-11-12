package com.kevinmdunne.chess.ui;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JFrame;

import com.google.common.eventbus.Subscribe;
import com.kevinmdunne.chess.controller.GameController;
import com.kevinmdunne.chess.controller.events.GameOverEvent;
import com.kevinmdunne.chess.controller.events.GameStartedEvent;
import com.kevinmdunne.chess.controller.events.ModelUpdatedEvent;
import com.kevinmdunne.chess.controller.events.PieceMovedEvent;
import com.kevinmdunne.chess.controller.events.PieceTakenEvent;
import com.kevinmdunne.chess.controller.events.PlayerInCheckEvent;
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

		this.boardui = new BoardUI(this);
		this.boardui.setBoard(this.controller.getModel());
		this.add(BorderLayout.CENTER,this.boardui);
		
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
	
	@Subscribe
	public void handlePlayerInCheck(PlayerInCheckEvent e){
		String message = "Black player is in check!";
		if(e.isWhite()){
			message = "White player is in check!";
		}
		this.messagePanel.setMessage(message, MessagePanel.ERROR_MESSAGE);
	}
	
	@Subscribe
	public void handleGameOver(GameOverEvent e){
		String message = "Black player WINS!!!";
		if(e.didWhiteWin()){
			message = "White player WINS!!!";
		}
		this.messagePanel.setMessage(message, MessagePanel.HIGHLIGHT_MESSAGE);
	}
	
	@Subscribe
	public void handlePieceTaken(PieceTakenEvent e){
		Point takenPieceLocation = e.getTakenPieceLocation();
		this.boardui.takePiece(takenPieceLocation);
	}
	
	@Subscribe
	public void handlePieceMoved(PieceMovedEvent e){
		this.boardui.pieceMoved(e.getFrom(),e.getTo());
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
