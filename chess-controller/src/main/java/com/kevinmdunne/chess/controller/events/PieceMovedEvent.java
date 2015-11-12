package com.kevinmdunne.chess.controller.events;

import java.awt.Point;

public class PieceMovedEvent {

	private Point from;
	private Point to;
	
	public PieceMovedEvent(Point from,Point to){
		this.from = from;
		this.to = to;
	}
	
	public Point getFrom(){
		return this.from;
	}
	
	public Point getTo(){
		return this.to;
	}
}
