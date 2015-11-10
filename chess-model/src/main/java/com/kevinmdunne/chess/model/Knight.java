package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.Collections;
import java.util.List;

public class Knight extends Piece {

	public Knight(boolean white) {
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

		return ((xDistance == 2 && yDistance == 1) || (xDistance == 1 && yDistance == 2));
	}

	@Override
	public List<Point> getInterveningSpaces(Space from, Space to) {
		return Collections.emptyList();
	}
}
