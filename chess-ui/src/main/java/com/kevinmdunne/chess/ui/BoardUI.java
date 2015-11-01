package com.kevinmdunne.chess.ui;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
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
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class BoardUI extends MouseAdapter {

	private Board board;
	private PickCanvas pickCanvas;
	
	private SpaceUI[][] spaces;

	public BoardUI(JPanel container) {
		this.createUI(container);
	}

	private void createUI(JPanel container) {
		container.setLayout(new BorderLayout());

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);

		container.add(BorderLayout.CENTER, canvas);

		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();

		TransformGroup tranformGroup = new TransformGroup();
		tranformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tranformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		MouseRotate behavior = new MouseRotate();
		behavior.setFactor(0.01f);
		behavior.setTransformGroup(tranformGroup);
		behavior.setSchedulingBounds(bounds);	
		tranformGroup.addChild(behavior);
		
		createBoard(tranformGroup);

		BranchGroup group = new BranchGroup();
		group.addChild(tranformGroup);
		addLights(group);
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
		
		rotateBoard(tranformGroup);
		
		pickCanvas = new PickCanvas(canvas, group);
		pickCanvas.setMode(PickCanvas.GEOMETRY);
		
		universe.getCanvas().addMouseListener(this);
		universe.getCanvas().addMouseMotionListener(this);
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
	
	private void placePieces(){
		for(int x = 0;x < 8;x++){
			for(int y = 0;y < 8;y++){
				Space space = this.board.getSpace(x, y);
				if(space.isOccupied()){
					Piece piece = space.getOccupant();
					SpaceUI spaceUI = this.spaces[x][y];
					PieceUI pieceUI = new PieceUI(piece);
					Group parentGroup = (Group)spaceUI.getParent();
					
					Vector3f vector = new Vector3f(0, 0.06f, 0);
					TransformGroup tg = new TransformGroup();
					Transform3D transform = new Transform3D();
					transform.setTranslation(vector);
					tg.setTransform(transform);
					tg.addChild(pieceUI);
					
					BranchGroup bg = new BranchGroup();
					bg.addChild(tg);
					parentGroup.addChild(bg);
				}
			}
		}
	}
	
	private void rotateBoard(TransformGroup tranformGroup){
		Transform3D transformX = new Transform3D();
		transformX.rotX(0.3f);
		tranformGroup.setTransform(transformX);
	}

	public void setBoard(Board board) {
		this.board = board;
		this.placePieces();
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
	
	public void mouseClicked(MouseEvent e){
		pickCanvas.setShapeLocation(e);
		PickResult result = pickCanvas.pickClosest();
		
		if (result != null) {
			Primitive p = (Primitive) result.getNode(PickResult.PRIMITIVE);

			if (p != null) {
				System.out.println(p.getClass().getName());
				//p.getAppearance().getMaterial().setDiffuseColor(0.0f, 0.0f, 1.0f);
				((ISelectable)p).select();

			} 
		}

	}
	
}
