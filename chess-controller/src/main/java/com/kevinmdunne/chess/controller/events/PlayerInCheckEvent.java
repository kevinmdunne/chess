package com.kevinmdunne.chess.controller.events;

public class PlayerInCheckEvent {

	private boolean white;
	
	public PlayerInCheckEvent(boolean white){
		this.white = white;
	}
	
	public boolean isWhite(){
		return this.white;
	}
}
