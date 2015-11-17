package com.kevinmdunne.chess.model;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.util.List;

import com.kevinmdunne.chess.exception.MoveException;

public abstract class Piece {

	private boolean white;
	
	public abstract List<Point> getInterveningSpaces(Space from, Space to);
	
	public abstract boolean isMoveLegal(Space from, Space to);
	
	public abstract List<Space> getAllPossibleMoves(Board board,Space origin);
	
	public Piece(boolean white){
		this.white = white;
	}
	
	public boolean isWhite(){
		return this.white;
	}
	
	public void move(Space from, Space to) throws MoveException{
		if(canMove(from,to)){
			from.vacate();
			to.occupy(this);
		}else{
			throw new MoveException("Invalid move for this piece");
		}
	}
	
	public boolean canMove(Space from, Space to){
		boolean result = from.getOccupant().equals(this);
		if(result){
			result = (!to.isOccupied() || to.getOccupant().isWhite() != this.isWhite());
			if(result){
				result = isMoveLegal(from, to);
			}
		}
		return result;
	}
	
	public Piece clone(){
		try{
			Class<?> clazz = this.getClass();
			Constructor<?>[] constructor = clazz.getConstructors();
			Object instance = constructor[0].newInstance(this.white);
			return (Piece)instance;
		}catch(Exception e){
			return null;
		}
	}
}
