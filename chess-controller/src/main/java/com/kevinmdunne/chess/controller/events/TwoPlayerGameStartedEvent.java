package com.kevinmdunne.chess.controller.events;

public class TwoPlayerGameStartedEvent {

	private boolean white;
	
	public TwoPlayerGameStartedEvent(boolean white){
		this.white = white;
	}
	
	public boolean isWhite(){
		return this.white;
	}
}
