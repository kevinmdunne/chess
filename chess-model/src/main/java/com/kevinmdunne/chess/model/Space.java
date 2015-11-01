package com.kevinmdunne.chess.model;

public class Space {

	private Piece occupant;
	private boolean white;
	private int x;
	private int y;
	
	public Space(int x,int y,boolean white){
		this.white = white;
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public boolean isWhite(){
		return this.white;
	}
	
	public Piece getOccupant(){
		return this.occupant;
	}
	
	public boolean isOccupied(){
		return this.occupant != null;
	}
	
	public void vacate(){
		this.occupant = null;
	}
	
	public void occupy(Piece occupant){
		this.occupant = occupant;
	}
}
