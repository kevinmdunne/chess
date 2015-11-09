package com.kevinmdunne.chess.controller;

import java.awt.Point;

import com.google.common.eventbus.EventBus;
import com.kevinmdunne.chess.controller.events.GameStartedEvent;
import com.kevinmdunne.chess.controller.events.ModelUpdatedEvent;
import com.kevinmdunne.chess.exception.MoveException;
import com.kevinmdunne.chess.model.Board;
import com.kevinmdunne.chess.model.Piece;

public class GameController {
	
	private boolean whitePlayersTurn;
	private EventBus eventBus;
	private Board model;
	
	public GameController(){
		this.eventBus = new EventBus();
		this.model = new Board();
	}
	
	public Board getModel(){
		return this.model;
	}
	
	public void startGame(){
		this.whitePlayersTurn = true;
		this.model.reset();
		this.eventBus.post(new GameStartedEvent());
	}
	
	public void move(Point from, Point to){
		try{
			this.model.movePiece(from, to);
			this.whitePlayersTurn = !this.whitePlayersTurn;
			this.eventBus.post(new ModelUpdatedEvent());
		}catch(MoveException e){
			this.eventBus.post(e);
		}
	}
	
	public void move(Piece piece, Point destination){
		try{
			this.model.movePiece(piece, destination);
			this.whitePlayersTurn = !this.whitePlayersTurn;
			this.eventBus.post(new ModelUpdatedEvent());
		}catch(MoveException e){
			this.eventBus.post(e);
		}
	}
	
	public boolean isWhitePlayersTurn(){
		return this.whitePlayersTurn;
	}
	
	public void registerListener(Object listener){
		this.eventBus.register(listener);
	}
}
