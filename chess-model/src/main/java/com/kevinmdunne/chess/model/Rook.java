package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

	public Rook(boolean white) {
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

		return ((xDistance != 0 && yDistance == 0) || (xDistance == 0 && yDistance != 0));
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
		
		if(xDistance == 0){
			int increment = 1;
				
			if(yDistance > 0){
				increment = -1;
			}
			
			int start = fromY + increment;
			int finish = toY - increment;
			
			if(increment > 0){
				for(int a = start;a <= finish;a = a + increment){
					Point point = new Point(fromX,a);
					result.add(point);
				}
			}else{
				for(int a = start;a >= finish;a = a + increment){
					Point point = new Point(fromX,a);
					result.add(point);
				}
			}
		}else{
			int increment = 1;
			
			if(xDistance > 0){
				increment = -1;
			}
			
			int start = fromX + increment;
			int finish = toX - increment;
			
			if(increment > 0){
				for(int a = start;a <= finish;a = a + increment){
					Point point = new Point(a,fromY);
					result.add(point);
				}
			}else{
				for(int a = start;a >= finish;a = a + increment){
					Point point = new Point(a,fromY);
					result.add(point);
				}
			}
		}
		
		return result;
	}
}
