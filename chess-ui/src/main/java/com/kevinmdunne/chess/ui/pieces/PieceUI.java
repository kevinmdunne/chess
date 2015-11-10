package com.kevinmdunne.chess.ui.pieces;

import com.kevinmdunne.chess.model.Piece;
import com.sun.j3d.utils.geometry.Primitive;

public interface PieceUI {
	
	public boolean isWhite();
	
	public Piece getModelledObject();
}
