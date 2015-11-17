package com.kevinmdunne.chess.controller.events;

import com.kevinmdunne.chess.model.Piece;

public class PieceTakenEvent {

	private Piece takenPiece;
	
	public PieceTakenEvent(Piece takenPiece){
		this.takenPiece = takenPiece;
	}
	
	public Piece getTakenPiece(){
		return this.takenPiece;
	}
}
