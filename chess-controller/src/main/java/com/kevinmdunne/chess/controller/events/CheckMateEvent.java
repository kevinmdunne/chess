package com.kevinmdunne.chess.controller.events;

public class CheckMateEvent {

	private boolean white;
	
	public CheckMateEvent(boolean white){
		this.white = white;
	}
	
	public boolean isWhite(){
		return this.white;
	}
}
