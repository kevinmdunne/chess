package com.kevinmdunne.chess.remote;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;

public class RemoteGameMoveListenerThread implements Runnable{
	
	private BufferedReader inputReader;
	private IRemoteGameHandler handler;
	private boolean running;
	
	public RemoteGameMoveListenerThread(BufferedReader inputReader,IRemoteGameHandler handler){
		this.inputReader = inputReader;
		this.handler = handler;
	}
	
	public void start(){
		this.running = true;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop(){
		this.running = false;
	}
	
	@Override
	public void run() {
		int reattempts = 0;
		while(this.running){
			try{
				String line = this.inputReader.readLine();
				if(line != null){
					String[] tokens = line.split(",");
					String fromx = tokens[0];
					String fromy = tokens[1];
					String tox = tokens[2];
					String toy = tokens[3];
					Point from = new Point(Integer.parseInt(fromx),Integer.parseInt(fromy));
					Point to = new Point(Integer.parseInt(tox),Integer.parseInt(toy));
					this.handler.moveMade(from, to);
				}
				Thread.sleep(1000);
			}catch(IOException e){
				if(reattempts > 3){
					System.out.println("Cannot read from socket for remote game. Finishing game.");
					this.handler.finishGame();
				}else{
					reattempts++;
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
