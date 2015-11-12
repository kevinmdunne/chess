package com.kevinmdunne.chess.controller.events;

public class GameOverEvent {

	private boolean whiteWon;
	
	public GameOverEvent(boolean whiteWon){
		this.whiteWon = whiteWon;
	}
	
	public boolean didWhiteWin(){
		return this.whiteWon;
	}
}
