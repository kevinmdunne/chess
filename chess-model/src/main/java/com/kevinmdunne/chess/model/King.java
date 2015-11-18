package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class King extends Piece {

	public King(boolean white) {
		super(white);
	}
	
	@Override
	public List<Space> getAllPossibleMoves(Board board,Space origin) {
		List<Space> result = new ArrayList<>();
		
		int originX = origin.getX();
		int originY = origin.getY();
		
		Space move1 = board.getSpace(new Point (originX,originY + 1));
		Space move2 = board.getSpace(new Point (originX,originY - 1));
		Space move3 = board.getSpace(new Point (originX + 1,originY));
		Space move4 = board.getSpace(new Point (originX - 1,originY + 1));
		Space move5 = board.getSpace(new Point (originX + 1,originY + 1));
		Space move6 = board.getSpace(new Point (originX - 1,originY - 1));
		Space move7 = board.getSpace(new Point (originX + 1,originY - 1));
		Space move8 = board.getSpace(new Point (originX - 1,originY + 1));
		Space move9 = board.getSpace(new Point (originX - 1,originY));
		
		if(move1 != null && (!move1.isOccupied() || move1.getOccupant().isWhite() != this.isWhite())){
			result.add(move1);
		}
		if(move2 != null && (!move2.isOccupied() || move2.getOccupant().isWhite() != this.isWhite())){
			result.add(move2);
		}
		if(move3 != null && (!move3.isOccupied() || move3.getOccupant().isWhite() != this.isWhite())){
			result.add(move3);
		}
		if(move4 != null && (!move4.isOccupied() || move4.getOccupant().isWhite() != this.isWhite())){
			result.add(move4);
		}
		if(move5 != null && (!move5.isOccupied() || move5.getOccupant().isWhite() != this.isWhite())){
			result.add(move5);
		}
		if(move6 != null && (!move6.isOccupied() || move6.getOccupant().isWhite() != this.isWhite())){
			result.add(move6);
		}
		if(move7 != null && (!move7.isOccupied() || move7.getOccupant().isWhite() != this.isWhite())){
			result.add(move7);
		}
		if(move8 != null && (!move8.isOccupied() || move8.getOccupant().isWhite() != this.isWhite())){
			result.add(move8);
		}
		if(move9 != null && (!move9.isOccupied() || move9.getOccupant().isWhite() != this.isWhite())){
			result.add(move9);
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

		return ((xDistance == 1 && yDistance == 0) || (yDistance == 1 && xDistance == 0) || (yDistance == 1 && xDistance == 1));

	}

	@Override
	public List<Point> getInterveningSpaces(Space from, Space to) {
		return Collections.emptyList();
	}

}
