package com.kevinmdunne.chess.ui.pieces;

import com.kevinmdunne.chess.model.Piece;

public interface PieceUI {
	
	public boolean isWhite();
	
	public Piece getModelledObject();
	
	public float getYOffset();
}
