package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

	public Bishop(boolean white) {
		super(white);
	}

	@Override
	public boolean isMoveLegal(Space from, Space to) {
		int fromY = from.getY();
		int toY = to.getY();
		int fromX = from.getX();
		int toX = to.getX();

		int xDistance = Math.abs(fromX - toX);
		int yDistance = Math.abs(fromY - toY);

		return (xDistance == yDistance);
	}

	@Override
	public List<Point> getInterveningSpaces(Space from, Space to) {
		List<Point> result = new ArrayList<>();
		
		int fromY = from.getY();
		int toY = to.getY();
		int fromX = from.getX();
		int toX = to.getX();
		
		int xDistance = (fromX - toX);
		int yDistance = (fromY - toY);
		
		int incrementX = 1;
		int incrementY = 1;
		
		if(xDistance > 0){
			incrementX = -1;
		}
		if(yDistance > 0){
			incrementY = -1;
		}
		
		int startX = fromX + incrementX;
		int startY = fromY + incrementY;
		int finishX = toX - incrementX;
		
		if(xDistance < 0){
			for(int x = startX, y = startY;x <= finishX;x = x + incrementX,y = y + incrementY){
				Point point = new Point(x,y);
				result.add(point);
			}
		}else{
			for(int x = startX, y = startY;x >= finishX;x = x + incrementX,y = y + incrementY){
				Point point = new Point(x,y);
				result.add(point);
			}
		}
		return result;
	}
}
