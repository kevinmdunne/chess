package com.kevinmdunne.chess.remote;

import java.awt.Point;

import com.kevinmdunne.chess.remote.exceptions.CannotJoinGameException;
import com.kevinmdunne.chess.remote.exceptions.CannotStartGameException;

public interface IRemoteGameHandler {

	public void startGame() throws CannotStartGameException;
	
	public void joinGame(String hostName) throws CannotJoinGameException;
	
	public void finishGame();
	
	public void sendMove(Point from, Point to);
	
	public void moveMade(Point from, Point to);
	
	public boolean isWhitePlayer();
}
