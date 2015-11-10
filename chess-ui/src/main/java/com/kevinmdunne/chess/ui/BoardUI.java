package com.kevinmdunne.chess.ui;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.kevinmdunne.chess.model.Board;
import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.model.Space;
import com.kevinmdunne.chess.ui.message.MessagePanel;
import com.kevinmdunne.chess.ui.pieces.PieceFactory;
import com.kevinmdunne.chess.ui.pieces.PieceUI;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class BoardUI extends MouseAdapter {

	private Board board;
	private PickCanvas pickCanvas;
	private BranchGroup topLevelGroup;
	private TransformGroup topLevelGroup2;
	private SimpleUniverse universe;
	
	private GameUI parent;
	
	private ISelectable selectedPiece;
	
	private SpaceUI[][] spaces;

	public BoardUI(GameUI parent,JPanel container) {
		this.parent = parent;
		this.createUI(container);
	}

	private void createUI(JPanel container) {
		container.setLayout(new BorderLayout());

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		container.add(BorderLayout.CENTER, canvas);

		universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		topLevelGroup = new BranchGroup();
		topLevelGroup.setCapability(BranchGroup.ALLOW_DETACH);
		
		topLevelGroup2 = new TransformGroup();
		topLevelGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		topLevelGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		MouseRotate behavior = new MouseRotate();
		behavior.setFactor(0.01f);
		behavior.setTransformGroup(topLevelGroup2);
		behavior.setSchedulingBounds(bounds);	
		topLevelGroup2.addChild(behavior);
		createBoard(topLevelGroup2);
		topLevelGroup.addChild(topLevelGroup2);
		
		addLights(topLevelGroup);
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(topLevelGroup);
		
		rotateBoard(topLevelGroup2);
		
		pickCanvas = new PickCanvas(canvas, topLevelGroup);
		pickCanvas.setMode(PickCanvas.GEOMETRY);
		
		universe.getCanvas().addMouseListener(this);
		universe.getCanvas().addMouseMotionListener(this);
		
		canvas.setDoubleBufferEnable(true);
	}
	
	private void createBoard(Group group){
		float xOffset = 0.5f;
		float zOffset = 0.5f;
		
		this.spaces = new SpaceUI[8][8];
		
		boolean white = false;
		for(int x = 0;x < 8;x++){
			white = !white;
			for(int y = 0;y < 8;y++){
				this.spaces[x][y] = new SpaceUI(x,y,white);
				TransformGroup tg = new TransformGroup();
				tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
				Vector3f vector = new Vector3f(x/6.3f - xOffset,-0.01f,y/6.3f - zOffset);
				Transform3D transform = new Transform3D();
				transform.setTranslation(vector);
				tg.setTransform(transform);
				tg.addChild(this.spaces[x][y]);
				group.addChild(tg);
				white = !white;
			}
		}
	}
	
	public void placePieces(){
		PieceFactory factory = new PieceFactory();
		
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				Space space = this.board.getSpace(x, y);
				if(space.isOccupied()){
					Piece piece = space.getOccupant();
					SpaceUI spaceUI = this.spaces[x][y];
					PieceUI pieceUI = factory.createPieceUI(piece);
					Group parentGroup = (Group)spaceUI.getParent();
					
					Vector3f vector = new Vector3f(0, 0.06f, 0);
					TransformGroup tg = new TransformGroup();
					Transform3D transform = new Transform3D();
					transform.setTranslation(vector);
					tg.setTransform(transform);
					tg.addChild((Primitive)pieceUI);
					
					BranchGroup bg = new BranchGroup();
					bg.addChild(tg);
					parentGroup.addChild(bg);
				}
			}
		}
	}
	
	private void rotateBoard(TransformGroup tranformGroup){
		Transform3D transformX = new Transform3D();
		transformX.rotX(0.4f);
		tranformGroup.setTransform(transformX);
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void refresh(){
		this.topLevelGroup.detach();
		this.topLevelGroup.removeAllChildren();
		
		topLevelGroup2 = new TransformGroup();
		topLevelGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		topLevelGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		MouseRotate behavior = new MouseRotate();
		behavior.setFactor(0.01f);
		behavior.setTransformGroup(topLevelGroup2);
		behavior.setSchedulingBounds(bounds);	
		topLevelGroup2.addChild(behavior);
		createBoard(topLevelGroup2);
		topLevelGroup.addChild(topLevelGroup2);
		
		addLights(topLevelGroup);
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(topLevelGroup);

		rotateBoard(topLevelGroup2);
		
		this.placePieces();
//		Canvas3D canvas = this.universe.getCanvas();
//		canvas.repaint();
	}

	protected void addLights(BranchGroup b) {
		// Create a bounds for the lights
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
		// Set up the ambient light
		Color3f ambientColour = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLight = new AmbientLight(ambientColour);
		ambientLight.setInfluencingBounds(bounds);
		// Set up the directional light
		Color3f lightColour = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f lightDir = new Vector3f(-1.0f, -1.0f, -1.0f);
		DirectionalLight light = new DirectionalLight(lightColour, lightDir);
		light.setInfluencingBounds(bounds);
		// Add the lights to the BranchGroup
		b.addChild(ambientLight);
		b.addChild(light);
	}
	
	private boolean isSelectionValid(PickResult result){
		if(result != null){
			Primitive shape = (Primitive) result.getNode(PickResult.PRIMITIVE);
			if (shape != null) {
				if(shape instanceof PieceUI){
					PieceUI piece = (PieceUI)shape;
					if(parent.getController().isWhitePlayersTurn() == piece.isWhite()){
						return true;
					}
				}else if(shape instanceof SpaceUI){
					if(this.selectedPiece != null){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void mouseClicked(MouseEvent e){
		pickCanvas.setShapeLocation(e);
		PickResult result = pickCanvas.pickClosest();
		
		if(this.isSelectionValid(result)){
			ISelectable selected = (ISelectable) result.getNode(PickResult.PRIMITIVE);
			if(selected instanceof PieceUI){
				selected.select();
				if(this.selectedPiece != null){
					this.selectedPiece.deselect();
				}
				this.selectedPiece = (ISelectable)selected;
			}else if(selected instanceof SpaceUI){
				SpaceUI space = (SpaceUI)selected;
				Point to = new Point(space.getX(), space.getY());
				PieceUI pieceUI = (PieceUI)this.selectedPiece;
				this.parent.getController().move(pieceUI.getModelledObject(), to);
				this.selectedPiece.deselect();
				this.selectedPiece = null;
			}
		}else{
			this.parent.setMessage("Invalid selection in UI",MessagePanel.ERROR_MESSAGE);
		}
	}
	
}
