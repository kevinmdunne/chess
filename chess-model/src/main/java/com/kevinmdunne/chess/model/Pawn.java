package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

	public Pawn(boolean white) {
		super(white);
	}

	@Override
	public boolean isMoveLegal(Space from, Space to) {
		boolean result = true;

		int fromY = from.getY();
		int toY = to.getY();
		int fromX = from.getX();
		int toX = to.getX();
		
		int distanceY = Math.abs(fromY - toY);
		int distanceX = Math.abs(fromX - toX);
		
		if(distanceX == 1 && distanceY == 1 && to.isOccupied() && to.getOccupant().isWhite() != this.isWhite()){
			return true;
		}
		if (distanceY > 2 || distanceY < 1) {
			return false;
		} else if (distanceY == 2) {
			return (fromY == 1 || fromY == 6);
		}

		return result;
	}

	@Override
	public List<Point> getInterveningSpaces(Space from, Space to) {
		List<Point> result = new ArrayList<>();
		int fromY = from.getY();
		int toY = to.getY();
		int distance = Math.abs(fromY - toY);
		if(distance == 2){
			Point point = new Point(from.getX(),from.getY() - (fromY - toY));
			result.add(point);
		}
		return result;
	}
}
