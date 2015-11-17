package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

	public Rook(boolean white) {
		super(white);
	}
	
	@Override
	public List<Space> getAllPossibleMoves(Board board,Space origin) {
		List<Space> result = new ArrayList<>();
		
		int originX = origin.getX();
		int originY = origin.getY();
		
		int x = originX + 1;
		Space space = board.getSpace(x,originY);
		
		while(space != null && (!space.isOccupied())){
			result.add(space);
			x = x + 1;
			space = board.getSpace(x,originY);
		}
		if(space != null && (!space.isOccupied() || space.getOccupant().isWhite() != this.isWhite())){
			result.add(space);
		}
		
		x = originX - 1;
		space = board.getSpace(x,originY);
		
		while(space != null && (!space.isOccupied())){
			result.add(space);
			x = x - 1;
			space = board.getSpace(x,originY);
		}
		if(space != null && (!space.isOccupied() || space.getOccupant().isWhite() != this.isWhite())){
			result.add(space);
		}
		
		int y = originY - 1;
		space = board.getSpace(originX,y);
		
		while(space != null && (!space.isOccupied())){
			result.add(space);
			y = y - 1;
			space = board.getSpace(originX,y);
		}
		if(space != null && (!space.isOccupied() || space.getOccupant().isWhite() != this.isWhite())){
			result.add(space);
		}
		
		y = originY + 1;
		space = board.getSpace(originX,y);
		
		while(space != null && (!space.isOccupied())){
			result.add(space);
			y = y + 1;
			space = board.getSpace(originX,y);
		}
		if(space != null && (!space.isOccupied() || space.getOccupant().isWhite() != this.isWhite())){
			result.add(space);
		}
		
		return result;
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
