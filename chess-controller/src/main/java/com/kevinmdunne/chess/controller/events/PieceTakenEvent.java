package com.kevinmdunne.chess.controller.events;

import java.awt.Point;

public class PieceTakenEvent {

	private Point takenPieceLocation;
	
	public PieceTakenEvent(Point takenPieceLocation){
		this.takenPieceLocation = takenPieceLocation;
	}
	
	public Point getTakenPieceLocation(){
		return this.takenPieceLocation;
	}
}
