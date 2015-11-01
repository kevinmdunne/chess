package com.kevinmdunne.chess.exception;

public class MoveException extends Exception{

	private static final long serialVersionUID = 534349917234284543L;

	public MoveException(){
		super();
	}
	
	public MoveException(String message){
		super(message);
	}

}
