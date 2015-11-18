package com.kevinmdunne.chess.remote.exceptions;

public class CannotStartGameException extends Exception{

	private static final long serialVersionUID = 8726885653679798705L;

	public CannotStartGameException(){
		super();
	}
	
    public CannotStartGameException(String message) {
        super(message);
    }
	
	public CannotStartGameException(Exception cause){
		super(cause);
	}
}
