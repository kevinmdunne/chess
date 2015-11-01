package com.kevinmdunne.chess.controller;

public class GameController {
	
	private boolean whitePlayersTurn;
	
	public GameController(){
		
	}
	
	public void startGame(){
		this.whitePlayersTurn = true;
	}
	
	public boolean isWhitePlayersTurn(){
		return this.whitePlayersTurn;
	}
}
