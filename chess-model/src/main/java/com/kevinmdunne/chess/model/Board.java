package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.kevinmdunne.chess.exception.MoveException;


public class Board {

	private Space[][] spaces;
	private List<Piece> deadPieces;

	public Board(){
		this.deadPieces = new ArrayList<>(32);
		this.initSpaces();
		this.initPieces();
	}
	
	public void reset(){
		this.deadPieces = new ArrayList<>(32);
		this.initSpaces();
		this.initPieces();
	}
	
	private void initSpaces(){
		this.spaces = new Space[8][8];
		boolean white = true;
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				spaces[x][y] = new Space(x,y,white);
				white = !white;
			}
		}
	}
	
	private void initPieces(){
		//place pawns
		int y = 1;
		for(int x = 0;x < 8;x++){
			Piece pawn = new Pawn(true);
			this.spaces[x][y].occupy(pawn);
		}
		
		y = 6;
		for(int x = 0;x < 8;x++){
			Piece pawn = new Pawn(false);
			this.spaces[x][y].occupy(pawn);
		}
		
		Piece rookWhite1 = new Rook(true);
		Piece rookWhite2 = new Rook(true);
		Piece rookBlack1 = new Rook(false);
		Piece rookBlack2 = new Rook(false);
		
		Piece knightWhite1 = new Knight(true);
		Piece knightWhite2 = new Knight(true);
		Piece knightBlack1 = new Knight(false);
		Piece knightBlack2 = new Knight(false);
		
		Piece bishopWhite1 = new Bishop(true);
		Piece bishopWhite2 = new Bishop(true);
		Piece bishopBlack1 = new Bishop(false);
		Piece bishopBlack2 = new Bishop(false);
		
		Piece whiteKing = new King(true);
		Piece blackKing = new King(false);
		Piece whiteQueen = new Queen(true);
		Piece blackQueen = new Queen(false);
		
		this.spaces[0][0].occupy(rookWhite1);
		this.spaces[1][0].occupy(knightWhite1);
		this.spaces[2][0].occupy(bishopWhite1);
		this.spaces[3][0].occupy(whiteKing);
		this.spaces[4][0].occupy(whiteQueen);
		this.spaces[5][0].occupy(bishopWhite2);
		this.spaces[6][0].occupy(knightWhite2);
		this.spaces[7][0].occupy(rookWhite2);
		
		this.spaces[0][7].occupy(rookBlack1);
		this.spaces[1][7].occupy(knightBlack1);
		this.spaces[2][7].occupy(bishopBlack1);
		this.spaces[3][7].occupy(blackKing);
		this.spaces[4][7].occupy(blackQueen);
		this.spaces[5][7].occupy(bishopBlack2);
		this.spaces[6][7].occupy(knightBlack2);
		this.spaces[7][7].occupy(rookBlack2);
	}
	
	public void movePiece(Point from, Point to) throws MoveException{
		Space fromSpace = this.getSpace(from.x, from.y);
		Space toSpace = this.getSpace(to.x, to.y);
		Piece piece = fromSpace.getOccupant();
		
		if(toSpace.isOccupied()){
			this.deadPieces.add(toSpace.getOccupant());
		}
		piece.move(fromSpace, toSpace);
	}
	
	public void movePiece(Piece piece, Point destination) throws MoveException{
		Space fromSpace = this.getLocation(piece);
		Space toSpace = this.getSpace(destination.x, destination.y);
		if(toSpace.isOccupied()){
			this.deadPieces.add(toSpace.getOccupant());
		}
		piece.move(fromSpace, toSpace);
	}
	
	public List<Piece> getDeadPieces(){
		return this.deadPieces;
	}
	
	public Space getSpace(int x,int y){
		return this.spaces[x][y];
	}
	
	public Space getLocation(Piece piece){
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				Space space = this.spaces[x][y];
				if(space.isOccupied()){
					Piece occupant = space.getOccupant();
					if(occupant.equals(piece)){
						return space;
					}
				}
			}
		}
		return null;
	}
}
