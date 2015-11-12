package com.kevinmdunne.chess.controller;

import java.awt.Point;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.kevinmdunne.chess.controller.events.GameOverEvent;
import com.kevinmdunne.chess.controller.events.GameStartedEvent;
import com.kevinmdunne.chess.controller.events.ModelUpdatedEvent;
import com.kevinmdunne.chess.controller.events.PieceMovedEvent;
import com.kevinmdunne.chess.controller.events.PieceTakenEvent;
import com.kevinmdunne.chess.controller.events.PlayerInCheckEvent;
import com.kevinmdunne.chess.exception.MoveException;
import com.kevinmdunne.chess.model.Board;
import com.kevinmdunne.chess.model.King;
import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.model.Space;

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
	
	private void detectPlayerInCheck(){
		if(this.model.isKingInCheck(false)){
			this.eventBus.post(new PlayerInCheckEvent(false));
		}else if(this.model.isKingInCheck(true)){
			this.eventBus.post(new PlayerInCheckEvent(true));
		}
	}
	
	private boolean isKingDead(boolean white){
		List<Piece> deadPieces = this.model.getDeadPieces();
		for(Piece deadPiece : deadPieces){
			if(deadPiece.isWhite() == white){
				if(deadPiece instanceof King){
					return true;
				}
			}
		}
		return false;
	}
	
	private void checkIfGameOver(){
		if(this.isKingDead(true)){
			this.eventBus.post(new GameOverEvent(false));
		}else if(this.isKingDead(false)){
			this.eventBus.post(new GameOverEvent(true));
		}
	}
	
	public void move(Point from, Point to){
		try{
			List<Piece> deadPieces = this.model.getDeadPieces();
			int deadPieceCount = deadPieces.size();
			this.model.movePiece(from, to);
			this.whitePlayersTurn = !this.whitePlayersTurn;
			if(deadPieceCount < deadPieces.size()){
				this.eventBus.post(new PieceTakenEvent(to));
			}
			this.eventBus.post(new PieceMovedEvent(from,to));
			this.eventBus.post(new ModelUpdatedEvent());
			this.detectPlayerInCheck();
			this.checkIfGameOver();
		}catch(MoveException e){
			this.eventBus.post(e);
		}
	}
	
	public void move(Piece piece, Point destination){
		try{
			List<Piece> deadPieces = this.model.getDeadPieces();
			int deadPieceCount = deadPieces.size();
			this.model.movePiece(piece, destination);
			this.whitePlayersTurn = !this.whitePlayersTurn;
			if(deadPieceCount < deadPieces.size()){
				this.eventBus.post(new PieceTakenEvent(destination));
			}
			Space fromSpace = this.model.getLocation(piece);
			this.eventBus.post(new PieceMovedEvent(new Point(fromSpace.getX(),fromSpace.getY()),destination));
			this.eventBus.post(new ModelUpdatedEvent());
			this.detectPlayerInCheck();
			this.checkIfGameOver();
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
