package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knight extends Piece {

	public Knight(boolean white) {
		super(white);
	}
	
	@Override
	public List<Space> getAllPossibleMoves(Board board,Space origin) {
		List<Space> result = new ArrayList<>();
		
		int originX = origin.getX();
		int originY = origin.getY();
		
		Space space1 = board.getSpace(originX + 1,originY + 2);
		Space space2 = board.getSpace(originX - 1,originY + 2);
		Space space3 = board.getSpace(originX - 1,originY - 2);
		Space space4 = board.getSpace(originX + 1,originY - 2);
		Space space5 = board.getSpace(originX + 2,originY + 1);
		Space space6 = board.getSpace(originX - 2,originY + 1);
		Space space7 = board.getSpace(originX - 2,originY - 1);
		Space space8 = board.getSpace(originX + 2,originY - 1);
		
		if(space1 != null && (!space1.isOccupied() || space1.getOccupant().isWhite() != this.isWhite())){
			result.add(space1);
		}
		if(space2 != null && (!space2.isOccupied() || space2.getOccupant().isWhite() != this.isWhite())){
			result.add(space2);
		}
		if(space3 != null && (!space3.isOccupied() || space3.getOccupant().isWhite() != this.isWhite())){
			result.add(space3);
		}
		if(space4 != null && (!space4.isOccupied() || space4.getOccupant().isWhite() != this.isWhite())){
			result.add(space4);
		}
		if(space5 != null && (!space5.isOccupied() || space5.getOccupant().isWhite() != this.isWhite())){
			result.add(space5);
		}
		if(space6 != null && (!space6.isOccupied() || space6.getOccupant().isWhite() != this.isWhite())){
			result.add(space6);
		}
		if(space7 != null && (!space7.isOccupied() || space7.getOccupant().isWhite() != this.isWhite())){
			result.add(space7);
		}
		if(space8 != null && (!space8.isOccupied() || space8.getOccupant().isWhite() != this.isWhite())){
			result.add(space8);
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

		return ((xDistance == 2 && yDistance == 1) || (xDistance == 1 && yDistance == 2));
	}

	@Override
	public List<Point> getInterveningSpaces(Space from, Space to) {
		return Collections.emptyList();
	}
}
