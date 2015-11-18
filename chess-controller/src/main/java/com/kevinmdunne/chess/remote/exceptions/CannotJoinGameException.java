package com.kevinmdunne.chess.remote.exceptions;

public class CannotJoinGameException extends Exception{

	private static final long serialVersionUID = -1599977621283519032L;

	public CannotJoinGameException(){
		super();
	}
	
    public CannotJoinGameException(String message) {
        super(message);
    }
	
	public CannotJoinGameException(Exception cause){
		super(cause);
	}
}
