package com.kevinmdunne.chess.model;

import com.kevinmdunne.chess.exception.MoveException;

public abstract class Piece {

	private boolean white;
	
	public Piece(boolean white){
		this.white = white;
	}
	
	public boolean isWhite(){
		return this.white;
	}
	
	public void move(Space from, Space to) throws MoveException{
		if(canMove(from,to)){
			from.vacate();
			to.occupy(this);
		}else{
			throw new MoveException("Invalid move for this piece");
		}
	}
	
	public boolean canMove(Space from, Space to){
		return from.getOccupant().equals(this);
	}
}
