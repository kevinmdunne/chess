package com.kevinmdunne.chess.controller.events;

import java.awt.Point;

import com.kevinmdunne.chess.model.Piece;

public class PieceMovedEvent {

	private Piece piece;
	private Point to;
	
	public PieceMovedEvent(Piece piece,Point to){
		this.piece = piece;
		this.to = to;
	}
	
	public Piece getPiece(){
		return this.piece;
	}
	
	public Point getTo(){
		return this.to;
	}
}
