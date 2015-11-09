package com.kevinmdunne.chess.model;

public class Bishop extends Piece {

	public Bishop(boolean white) {
		super(white);
	}
	
	@Override
	public boolean canMove(Space from, Space to) {
		boolean result = super.canMove(from, to);
		if(result){
			int fromY = from.getY();
			int toY = to.getY();
			int fromX = from.getX();
			int toX = to.getX();
			
			int xDistance = Math.abs(fromX - toX);
			int yDistance = Math.abs(fromY - toY);
			
			result = (xDistance == yDistance);
		}
		
		return result;
	}
}
