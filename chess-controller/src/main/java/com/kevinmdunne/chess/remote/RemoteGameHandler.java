package com.kevinmdunne.chess.remote;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.kevinmdunne.chess.controller.GameController;
import com.kevinmdunne.chess.remote.exceptions.CannotJoinGameException;
import com.kevinmdunne.chess.remote.exceptions.CannotStartGameException;

public class RemoteGameHandler implements IRemoteGameHandler{
	
	private static final int GAME_PORT = 1111;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	private boolean white;
	
	private GameController controller;
	
	private RemoteGameMoveListenerThread moveListenerThread;
	
	public RemoteGameHandler(GameController controller){
		this.controller = controller;
	}
	
	@Override
	public void startGame() throws CannotStartGameException{
		try{
			this.serverSocket = new ServerSocket(GAME_PORT);
			this.clientSocket = serverSocket.accept();
			
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);                   
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        this.moveListenerThread = new RemoteGameMoveListenerThread(this.in,this);
	        this.moveListenerThread.start();
	        this.controller.startGame();
	        this.white = true;
	        this.controller.initializeRemoteGame(this);
		}catch(IOException e){
			throw new CannotStartGameException(e);
		}
	}

	@Override
	public void joinGame(String hostName) throws CannotJoinGameException{
		try{
			this.clientSocket = new Socket(hostName, GAME_PORT);
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.moveListenerThread = new RemoteGameMoveListenerThread(this.in,this);
	        this.moveListenerThread.start();
	        this.controller.startGame();
	        this.white = false; 
	        this.controller.initializeRemoteGame(this);
		}catch(IOException e){
			throw new CannotJoinGameException(e);
		}
	}
	
	@Override
	public void finishGame() {
		try{
			if(this.moveListenerThread != null)
				this.moveListenerThread.stop();
			if(this.in != null)
				this.in.close();
			if(this.out != null)
				this.out.close();
			if(this.serverSocket != null)
				this.serverSocket.close();
			if(this.clientSocket != null)
				this.clientSocket.close();
			
			this.controller.endGame();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendMove(Point from, Point to) {
		String packet = from.x + "," + from.y + "," + to.x + "," + to.y;
		this.out.println(packet);
	}
	
	@Override
	public void moveMade(Point from, Point to) {
		this.controller.remoteMove(from,to);
	}
	
	@Override
	public boolean isWhitePlayer() {
		return white;
	}
}
