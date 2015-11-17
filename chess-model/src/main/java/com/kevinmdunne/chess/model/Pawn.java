package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

	public Pawn(boolean white) {
		super(white);
	}
	
	@Override
	public List<Space> getAllPossibleMoves(Board board,Space origin) {
		List<Space> result = new ArrayList<>();
		int direction = this.isWhite() ? 1 : -1;
		int originX = origin.getX();
		int originY = origin.getY();
		
		Space inFrontOf1 = board.getSpace(new Point (originX,originY + direction));
		Space inFrontOf2 = board.getSpace(new Point (originX,originY + direction + direction));
		Space diagonal1 = board.getSpace(new Point(originX + 1,originY + direction));
		Space diagonal2 = board.getSpace(new Point(originX - 1,originY + direction));
		
		if(inFrontOf1 != null && !inFrontOf1.isOccupied()){
			result.add(inFrontOf1);
		}
		
		if(diagonal1 != null){
			if(diagonal1.isOccupied() && diagonal1.getOccupant().isWhite() != this.isWhite()){
				result.add(diagonal1);
			}
		}
		
		if(diagonal2 != null){
			if(diagonal2.isOccupied() && diagonal2.getOccupant().isWhite() != this.isWhite()){
				result.add(diagonal2);
			}
		}
		
		if((originY == 1 && isWhite()) || (originY == 6 && !isWhite())){
			if(inFrontOf2 != null && !inFrontOf2.isOccupied()){
				result.add(inFrontOf2);
			}
		}

		return result;
	}

	@Override
	public boolean isMoveLegal(Space from, Space to) {
		int fromY = from.getY();
		int toY = to.getY();
		int fromX = from.getX();
		int toX = to.getX();
		
		int distanceY = Math.abs(fromY - toY);
		int distanceX = Math.abs(fromX - toX);
		int direction = fromY - toY;
		
		if(direction > 0 && this.isWhite()){
			return false;
		}else if(direction < 0 && !this.isWhite()){
			return false;
		}
		
		if(distanceX == 1 && distanceY == 1 && to.isOccupied() && to.getOccupant().isWhite() != this.isWhite()){
			return true;
		}
		if (distanceY > 2 || distanceY < 1) {
			return false;
		} else if (distanceY == 2) {
			return (fromY == 1 || fromY == 6);
		} else if(distanceY == 1 && distanceX == 0){
			return true;
		}

		return false;
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
