package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.List;

public class Queen extends Piece {

	public Queen(boolean white) {
		super(white);
	}

	@Override
	public boolean isMoveLegal(Space from, Space to) {
		boolean result = false;

		int fromY = from.getY();
		int toY = to.getY();
		int fromX = from.getX();
		int toX = to.getX();

		int xDistance = Math.abs(fromX - toX);
		int yDistance = Math.abs(fromY - toY);

		result = ((xDistance != 0 && yDistance == 0) || (xDistance == 0 && yDistance != 0));
		if (!result) {
			result = (xDistance == yDistance);
		}

		return result;
	}

	@Override
	public List<Point> getInterveningSpaces(Space from, Space to) {
		
		int fromY = from.getY();
		int toY = to.getY();
		int fromX = from.getX();
		int toX = to.getX();

		int xDistance = (fromX - toX);
		int yDistance = (fromY - toY);
		
		if(xDistance == 0 || yDistance == 0){
			Rook rook = new Rook(true);
			return rook.getInterveningSpaces(from, to);
		}else{
			Bishop bishop = new Bishop(true);
			return bishop.getInterveningSpaces(from, to);
		}
	}
}
