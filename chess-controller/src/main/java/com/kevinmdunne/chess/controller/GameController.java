package com.kevinmdunne.chess.controller;

import java.awt.Point;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.kevinmdunne.chess.controller.events.CheckMateEvent;
import com.kevinmdunne.chess.controller.events.GameOverEvent;
import com.kevinmdunne.chess.controller.events.GameStartedEvent;
import com.kevinmdunne.chess.controller.events.ModelUpdatedEvent;
import com.kevinmdunne.chess.controller.events.PieceMovedEvent;
import com.kevinmdunne.chess.controller.events.PieceTakenEvent;
import com.kevinmdunne.chess.controller.events.PlayerInCheckEvent;
import com.kevinmdunne.chess.controller.events.TwoPlayerGameStartedEvent;
import com.kevinmdunne.chess.exception.MoveException;
import com.kevinmdunne.chess.model.Board;
import com.kevinmdunne.chess.model.King;
import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.model.Space;
import com.kevinmdunne.chess.remote.IRemoteGameHandler;

public class GameController {
	
	private boolean whitePlayersTurn;
	private EventBus eventBus;
	private Board model;
	private IRemoteGameHandler handler;
	
	public GameController(){
		this.eventBus = new EventBus();
		this.model = new Board();
	}
	
	public void initializeRemoteGame(IRemoteGameHandler handler){
		this.handler = handler;
		this.eventBus.post(new TwoPlayerGameStartedEvent(handler.isWhitePlayer()));
	}
	
	public boolean isMyPiece(boolean white){
		if(handler != null){
			return handler.isWhitePlayer() == white;
		}
		return true;
	}
	
	public Board getModel(){
		return this.model;
	}
	
	public void startGame(){
		this.whitePlayersTurn = true;
		this.model.reset();
		this.eventBus.post(new GameStartedEvent());
	}
	
	public void endGame(){
		this.eventBus.post(new GameOverEvent(whitePlayersTurn));
	}
	
	private boolean detectCheckMate(boolean white){
		return this.model.detectCheckMate(white);
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
	
	public void remoteMove(Point from, Point to){
		try{
			Space fromSpace = this.model.getSpace(from.x, from.y);
			Piece piece = fromSpace.getOccupant();
			
			List<Piece> deadPieces = this.model.getDeadPieces();
			int deadPieceCount = deadPieces.size();
			this.model.movePiece(from, to);
			this.whitePlayersTurn = !this.whitePlayersTurn;
			if(deadPieceCount < deadPieces.size()){
				this.eventBus.post(new PieceTakenEvent(deadPieces.get(deadPieceCount)));
			}
			
			this.eventBus.post(new PieceMovedEvent(piece,to));
			this.eventBus.post(new ModelUpdatedEvent());
			if(this.detectCheckMate(!piece.isWhite())){
				this.eventBus.post(new CheckMateEvent(!piece.isWhite()));
			}else{
				this.detectPlayerInCheck();
				this.checkIfGameOver();
			}
		}catch(MoveException e){
			this.eventBus.post(e);
		}
	}
	
	public void move(Point from, Point to){
		try{
			Space fromSpace = this.model.getSpace(from.x, from.y);
			Piece piece = fromSpace.getOccupant();
			
			List<Piece> deadPieces = this.model.getDeadPieces();
			int deadPieceCount = deadPieces.size();
			this.model.movePiece(from, to);
			this.whitePlayersTurn = !this.whitePlayersTurn;
			if(deadPieceCount < deadPieces.size()){
				this.eventBus.post(new PieceTakenEvent(deadPieces.get(deadPieceCount)));
			}
			
			this.eventBus.post(new PieceMovedEvent(piece,to));
			this.eventBus.post(new ModelUpdatedEvent());
			if(this.detectCheckMate(!piece.isWhite())){
				this.eventBus.post(new CheckMateEvent(!piece.isWhite()));
			}else{
				this.detectPlayerInCheck();
				this.checkIfGameOver();
			}
			
			if(this.handler != null){
				this.handler.sendMove(from, to);
			}
		}catch(MoveException e){
			this.eventBus.post(e);
		}
	}
	
	public void move(Piece piece, Point destination){
		try{
			List<Piece> deadPieces = this.model.getDeadPieces();
			int deadPieceCount = deadPieces.size();
			
			Space space = this.model.getLocation(piece);
			Point from = new Point(space.getX(),space.getY());
			
			this.model.movePiece(piece, destination);
			this.whitePlayersTurn = !this.whitePlayersTurn;
			if(deadPieceCount < deadPieces.size()){
				this.eventBus.post(new PieceTakenEvent(deadPieces.get(deadPieceCount)));
			}

			this.eventBus.post(new PieceMovedEvent(piece,destination));
			this.eventBus.post(new ModelUpdatedEvent());
			
			if(this.detectCheckMate(!piece.isWhite())){
				this.eventBus.post(new CheckMateEvent(!piece.isWhite()));
			}else{
				this.detectPlayerInCheck();
				this.checkIfGameOver();
			}
			
			if(this.handler != null){
				this.handler.sendMove(from, destination);
			}
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
