package com.kevinmdunne.chess.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kevinmdunne.chess.model.Board;
import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.ui.resources.PieceImageResourceRegistry;

public class DeadPiecesUI extends JPanel{

	private static final long serialVersionUID = -1685974715154750068L;
	
	private Board model;
	
	public DeadPiecesUI(Board model){
		super();
		this.model = model;
		this.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), "Dead Pieces"));
		this.setPreferredSize(new Dimension(190, 200));
	}
	
	private void displayDeadPieces(){
		PieceImageResourceRegistry registry = PieceImageResourceRegistry.getInstance();
		List<Piece> deadPieces = model.getDeadPieces();
		for(Piece piece : deadPieces){
			ImageIcon icon = registry.getImage(piece);
			JLabel label = new JLabel(icon);
			this.add(label);
		}
	}
	
	public void refresh(){
		this.removeAll();
		this.displayDeadPieces();
	}
}
