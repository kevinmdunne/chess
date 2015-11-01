package com.kevinmdunne.chess.model;

public class Pawn extends Piece{

	public Pawn(boolean white) {
		super(white);
	}
	
	@Override
	public boolean canMove(Space from, Space to) {
		boolean result = super.canMove(from, to);
		if(result){
			int fromY = from.getY();
			int toY = to.getY();
			int distance = Math.abs(fromY - toY);
			if(distance > 2 || distance < 1){
				result = false;
			}else if(distance == 2){
				result = (fromY == 1 || fromY == 6);
			}
		}
		return result;
	}

}
