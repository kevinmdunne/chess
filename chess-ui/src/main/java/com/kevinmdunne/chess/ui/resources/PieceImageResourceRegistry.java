package com.kevinmdunne.chess.ui.resources;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.kevinmdunne.chess.model.Bishop;
import com.kevinmdunne.chess.model.King;
import com.kevinmdunne.chess.model.Knight;
import com.kevinmdunne.chess.model.Pawn;
import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.model.Queen;
import com.kevinmdunne.chess.model.Rook;

public class PieceImageResourceRegistry {

	private static PieceImageResourceRegistry INSTANCE;
	
	private Map<String,ImageIcon> black_registry;
	private Map<String,ImageIcon> white_registry;
	
	private PieceImageResourceRegistry(){
		this.black_registry = new HashMap<>(6);
		this.white_registry = new HashMap<>(6);
		this.loadImages();
	}
	
	private void loadImages(){
		black_registry.put(Pawn.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/black_pawn.png")));
		black_registry.put(King.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/black_king.png")));
		black_registry.put(Knight.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/black_knight.png")));
		black_registry.put(Queen.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/black_queen.png")));
		black_registry.put(Rook.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/black_rook.png")));
		black_registry.put(Bishop.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/black_bishop.png")));
		
		white_registry.put(Pawn.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/white_pawn.png")));
		white_registry.put(King.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/white_king.png")));
		white_registry.put(Knight.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/white_knight.png")));
		white_registry.put(Queen.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/white_queen.png")));
		white_registry.put(Rook.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/white_rook.png")));
		white_registry.put(Bishop.class.getCanonicalName(), new ImageIcon(PieceImageResourceRegistry.class.getResource("/images/white_bishop.png")));
	}
	
	public ImageIcon getImage(Piece piece){
		if(piece.isWhite()){
			return this.white_registry.get(piece.getClass().getCanonicalName());
		}else{
			return this.black_registry.get(piece.getClass().getCanonicalName());
		}
	}
	
	public static PieceImageResourceRegistry getInstance(){
		if(INSTANCE == null){
			INSTANCE = new PieceImageResourceRegistry();
		}
		return INSTANCE;
	}
}
