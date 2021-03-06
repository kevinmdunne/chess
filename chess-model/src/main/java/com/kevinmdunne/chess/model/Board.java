package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.kevinmdunne.chess.exception.MoveException;


public class Board {

	private Space[][] spaces;
	private List<Piece> deadPieces;

	public Board(Board board){
		this.deadPieces = new ArrayList<>(32);
		spaces = new Space[8][8];
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				spaces[x][y] = board.getSpace(x, y).clone();
			}
		}
	}
	
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
	
	private boolean checkForCollision(Piece piece,Space from, Space to){
		List<Point> interveningSpaces = piece.getInterveningSpaces(from, to);
		for(Point point : interveningSpaces){
			Space space = this.spaces[point.x][point.y];
			if(space.isOccupied()){
				return true;
			}
		}
		return false;
	}
	
	private Space findKing(boolean white){
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				Space space = this.spaces[x][y];
				if(space.isOccupied()){
					Piece piece = space.getOccupant();
					if(piece.isWhite() == white){
						if(piece instanceof King){
							return space;
						}
					}
				}
			}
		}
		return null;
	}
	
	private List<Space> getAllPieces(boolean white){
		List<Space> result = new ArrayList<>();
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				Space space = this.spaces[x][y];
				if(space.isOccupied()){
					Piece piece = space.getOccupant();
					if(piece.isWhite() == white){
						result.add(space);
					}
				}
			}
		}
		return result;
	}
	
	public boolean isKingInCheck(boolean white){
		Space toSpace = this.findKing(white);
		List<Space> fromSpaces = this.getAllPieces(!white);
		for(Space fromSpace : fromSpaces){
			Piece piece = fromSpace.getOccupant();
			if(piece.canMove(fromSpace, toSpace)){
				if(!checkForCollision(piece,fromSpace,toSpace)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAnyPlayerInCheck(){
		boolean result = this.isKingInCheck(true);
		if(!result){
			result = this.isKingInCheck(false);
		}
		return result;
	}
	
	private boolean doesMoveCauseCheck(Space fromSpace, Space toSpace) throws MoveException{
		Board clone = new Board(this);
		Space cloneFromSpace = clone.getSpace(fromSpace.getX(), fromSpace.getY());
		Space cloneToSpace = clone.getSpace(toSpace.getX(), toSpace.getY());
		Piece piece = cloneFromSpace.getOccupant();
		piece.move(cloneFromSpace, cloneToSpace);
		return clone.isKingInCheck(piece.isWhite());
	}
	
	public boolean detectCheckMate(boolean white){
		boolean inCheck = this.isKingInCheck(white);
		if(inCheck){
			for(int x = 0;x < 8;x++){
				for(int y = 0;y < 8;y++){
					Space space = this.spaces[x][y];
					if(space.isOccupied()){
						Piece piece = space.getOccupant();
						if(piece.isWhite() == white){
							List<Space> possibleMoves = piece.getAllPossibleMoves(this,space);
							for(Space destination : possibleMoves){
								try{
									boolean stillInCheck = this.doesMoveCauseCheck(space, destination);
									if(!stillInCheck){
										return false;
									}
								}catch(MoveException e){
									//dodgy move, shouldnt happen
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public void movePiece(Space fromSpace, Space toSpace) throws MoveException{
		Piece piece = fromSpace.getOccupant();
		if(!checkForCollision(piece,fromSpace,toSpace)){
			if(!this.doesMoveCauseCheck(fromSpace,toSpace)){
				if(toSpace.isOccupied()){
					this.deadPieces.add(toSpace.getOccupant());
				}
				piece.move(fromSpace, toSpace);
			}else{
				throw new MoveException("That move will leave player in check.");
			}
		}else{
			throw new MoveException("Other pieces are in the way!");
		}
	}
	
	public void movePiece(Point from, Point to) throws MoveException{
		Space fromSpace = this.getSpace(from.x, from.y);
		Space toSpace = this.getSpace(to.x, to.y);
		this.movePiece(fromSpace, toSpace);
	}
	
	public void movePiece(Piece piece, Point destination) throws MoveException{
		Space fromSpace = this.getLocation(piece);
		Space toSpace = this.getSpace(destination.x, destination.y);
		this.movePiece(fromSpace, toSpace);
	}
	
	public List<Piece> getDeadPieces(){
		return this.deadPieces;
	}
	
	public Space getSpace(int x,int y){
		if(x < 0 || x >= 8 || y < 0 || y >= 8){
			return null;
		}
		return this.spaces[x][y];
	}
	
	public Space getSpace(Point point){
		if(point.x < 0 || point.x >= 8 || point.y < 0 || point.y >= 8){
			return null;
		}
		return this.spaces[point.x][point.y];
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
