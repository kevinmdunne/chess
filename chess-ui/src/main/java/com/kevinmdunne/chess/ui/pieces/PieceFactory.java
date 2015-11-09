package com.kevinmdunne.chess.ui.pieces;

import com.kevinmdunne.chess.model.Bishop;
import com.kevinmdunne.chess.model.King;
import com.kevinmdunne.chess.model.Knight;
import com.kevinmdunne.chess.model.Pawn;
import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.model.Queen;
import com.kevinmdunne.chess.model.Rook;

public class PieceFactory {

	public PieceFactory(){
		
	}
	
	public PieceUI createPieceUI(Piece modelObject){
		if(modelObject instanceof Pawn){
			return new PawnUI(modelObject);
		}else if(modelObject instanceof King){
			return new KingUI(modelObject);
		}else if(modelObject instanceof Queen){
			return new QueenUI(modelObject);
		}else if(modelObject instanceof Rook){
			return new RookUI(modelObject);
		}else if(modelObject instanceof Bishop){
			return new BishopUI(modelObject);
		}else if(modelObject instanceof Knight){
			return new KnightUI(modelObject);
		}
		return null;
	}
}
