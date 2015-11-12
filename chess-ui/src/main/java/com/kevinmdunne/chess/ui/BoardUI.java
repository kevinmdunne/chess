package com.kevinmdunne.chess.ui;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.BorderFactory;
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
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class BoardUI extends JPanel implements MouseListener{

	private static final long serialVersionUID = 5946533815295576557L;
	
	private GameUI parent;
	private SimpleUniverse universe;
	private BranchGroup contentGroup;
	private TransformGroup behaviourGroup;
	private PickCanvas pickCanvas;
	
	private ISelectable selectedPiece;
	
	private SpaceUI[][] spaces;
	
	private Board board;
	
	public BoardUI(GameUI parent){
		this.parent = parent;
		this.createUI();
	}
	
	public void createUI(){
		//create canvas and add to UI
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		canvas.setDoubleBufferEnable(true);
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, canvas);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//create universe
		this.universe = new SimpleUniverse(canvas);
		this.universe.getViewingPlatform().setNominalViewingTransform();
		
		//create toplevel container
		this.contentGroup = new BranchGroup();
		
		//add lighting
		this.addLights(this.contentGroup);
		
		//add mouse behaviors
		this.addBehaviours();
		
		//create the board
		this.createBoard(this.behaviourGroup);
		
		//rotate the board so it is easier to see
		this.rotateBoard(this.behaviourGroup);
		
		//add content group to universe
		this.universe.addBranchGraph(this.contentGroup);
		
		//set up pick canvas for user interaction
		this.pickCanvas = new PickCanvas(canvas, this.contentGroup);
		this.pickCanvas.setMode(PickCanvas.GEOMETRY);
		
		//add mouse listeners
		this.universe.getCanvas().addMouseListener(this);
	}
	
	private void rotateBoard(TransformGroup tranformGroup){
		Transform3D transformX = new Transform3D();
		transformX.rotX(0.4f);
		tranformGroup.setTransform(transformX);
	}
	
	private void createBoard(Group container){
		float xOffset = 0.5f;
		float zOffset = 0.5f;
		
		this.spaces = new SpaceUI[8][8];
		
		boolean white = false;
		for(int x = 0;x < 8;x++){
			white = !white;
			for(int y = 0;y < 8;y++){
				this.spaces[x][y] = new SpaceUI(x,y,white);
				TransformGroup transformGroup = new TransformGroup();
				transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
				transformGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
				Vector3f vector = new Vector3f(x/6.3f - xOffset,-0.01f,y/6.3f - zOffset);
				Transform3D transform = new Transform3D();
				transform.setTranslation(vector);
				transformGroup.setTransform(transform);
				transformGroup.addChild(this.spaces[x][y]);
				container.addChild(transformGroup);
				white = !white;
			}
		}
	}
	
	private void addBehaviours(){
		this.behaviourGroup = new TransformGroup();
		this.behaviourGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.behaviourGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.behaviourGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
		this.behaviourGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		MouseRotate behavior = new MouseRotate();
		behavior.setFactor(0.01f);
		behavior.setTransformGroup(this.behaviourGroup);
		behavior.setSchedulingBounds(bounds);	
		this.behaviourGroup.addChild(behavior);
		
		MouseWheelZoom zoomBehavior = new MouseWheelZoom();
		zoomBehavior.setFactor(0.09f);
		zoomBehavior.setSchedulingBounds(bounds);	
		zoomBehavior.setTransformGroup(this.behaviourGroup);
		this.behaviourGroup.addChild(zoomBehavior);
		
		this.contentGroup.addChild(this.behaviourGroup);
	}
	
	protected void addLights(BranchGroup group) {
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
		Color3f ambientColour = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLight = new AmbientLight(ambientColour);
		ambientLight.setInfluencingBounds(bounds);
		Color3f lightColour = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f lightDir = new Vector3f(-1.0f, -1.0f, -1.0f);
		DirectionalLight light = new DirectionalLight(lightColour, lightDir);
		light.setInfluencingBounds(bounds);
		group.addChild(ambientLight);
		group.addChild(light);
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	public void placePieces(){
		float xOffset = 0.5f;
		float zOffset = 0.5f;
		PieceFactory factory = new PieceFactory();
		
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				Space space = this.board.getSpace(x, y);
				if(space.isOccupied()){
					Piece piece = space.getOccupant();
					PieceUI pieceUI = factory.createPieceUI(piece);
					
					Vector3f vector = new Vector3f(x/6.3f - xOffset,pieceUI.getYOffset(),y/6.3f - zOffset);
					TransformGroup tranformGroup = new TransformGroup();
					tranformGroup.setName("PieceTransformGroup");
					tranformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
					tranformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
					Transform3D transform = new Transform3D();
					transform.setTranslation(vector);
					tranformGroup.setTransform(transform);
					tranformGroup.addChild((Primitive)pieceUI);
					
					BranchGroup branchGroup = new BranchGroup();
					branchGroup.setName("PieceBranchGroup");
					branchGroup.setUserData(piece);
					branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
					branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
					branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
					branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
					branchGroup.addChild(tranformGroup);
					this.behaviourGroup.addChild(branchGroup);
				}
			}
		}
	}
	
	public void refresh(){
		Enumeration<Object> children = this.behaviourGroup.getAllChildren();
		while(children.hasMoreElements()){
			Object child = children.nextElement();
			if(child instanceof BranchGroup){
				BranchGroup branchGroup = (BranchGroup)child;
				if(branchGroup.getName().equals("PieceBranchGroup")){
					this.behaviourGroup.removeChild(branchGroup);
				}
			}
		}
		this.placePieces();
	}
	
	public void pieceMoved(Point from,Point to){
		
	}
	
	public void takePiece(Point location){
//		Space space = this.board.getSpace(location.x, location.y);
//		Piece piece = space.getOccupant();
//		
//		Enumeration<Object> children = this.behaviourGroup.getAllChildren();
//		while(children.hasMoreElements()){
//			Object child = children.nextElement();
//			if(child instanceof BranchGroup){
//				BranchGroup branchGroup = (BranchGroup)child;
//				if(branchGroup.getName().equals("PieceBranchGroup")){
//					Object userData = branchGroup.getUserData();
//					if(userData.equals(piece)){
//						for(double a = 1.0;a >= 0;a = a - 0.01){
//							TransformGroup tg = new TransformGroup();
//							Transform3D t = new Transform3D();
//							t.setScale(a);
//							tg.setTransform(t);
//							branchGroup.addChild(tg);
//						}
//					}
//				}
//			}
//		}
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.pickCanvas.setShapeLocation(e);
		PickResult result = this.pickCanvas.pickClosest();
		
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

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
